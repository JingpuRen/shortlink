package org.gopher.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.gopher.shortlink.project.common.constant.RedisKeyConstant;
import org.gopher.shortlink.project.common.convention.exception.ServiceException;
import org.gopher.shortlink.project.dao.entity.*;
import org.gopher.shortlink.project.dao.mapper.*;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.gopher.shortlink.project.service.ShortLinkService;
import org.gopher.shortlink.project.util.HashUtil;
import org.gopher.shortlink.project.util.LinkUtil;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.gopher.shortlink.project.common.constant.RedisKeyConstant.UIP_STORE_KEY;
import static org.gopher.shortlink.project.common.constant.RedisKeyConstant.UV_StORE_KEY;
import static org.gopher.shortlink.project.common.constant.ShortLinkConstant.AMAP_REMOTE_URL;
import static org.gopher.shortlink.project.util.LinkUtil.getLinkCacheValidDate;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO>implements ShortLinkService {

    // 引入布隆过滤器
    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    private final ShortLinkGotoMapper shortLinkGotoMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private final RedissonClient redissonClient;

    private final LinkAccessStatsMapper linkAccessStatsMapper;

    private final LinkLocaleStatsMapper linkLocaleStatsMapper;

    @Value("${short-link.stats.locale.amap-key}")
    private String shortLinkStatsAmapKey;

    private final LinkOsStatsMapper linkOsStatsMapper;

    private final LinkBrowserStatsMapper linkBrowserStatsMapper;

    private final LinkDeviceStatsMapper linkDeviceStatsMapper;

    private final LinkNetworkStatsMapper linkNetworkStatsMapper;

    private final LinkAccessLogsMapper linkAccessLogsMapper;

    /**
     * 创建短链接
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        // 复制对象属性以生成新的实体层对象 tip : domain里面是没有http和https的
        ShortLinkDO shortLinkDO = BeanUtil.toBean(shortLinkCreateReqDTO, ShortLinkDO.class);
        // 生成短链接
        String shortUri = generateSuffix(shortLinkCreateReqDTO);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setEnableStatus(0);
        // tip 生成完整的短链接 ：http:// + 域名 + / + 后缀
        shortLinkDO.setFullShortUrl("http://" + shortLinkCreateReqDTO.getDomain() + "/" + shortUri);

        // 创建路由表的对象
        ShortLinkGotoDO shortLinkGotoDO = ShortLinkGotoDO.builder()
                .gid(shortLinkCreateReqDTO.getGid())
                .fullShortUrl("http://" + shortLinkCreateReqDTO.getDomain() + "/" + shortUri)
                .build();

        // 插入新生成的短链接，以及插入对应的路由表
        try{
            baseMapper.insert(shortLinkDO);
            shortLinkGotoMapper.insert(shortLinkGotoDO);
        }catch (DuplicateKeyException ex){
            // tip 这里主要是对布隆过滤器的误判进行预防，因为如果误判的话，但由于我们有唯一索引，因此数据库会报错的！！
            log.warn("ShortLinkServiceImpl\\createShortLink failed");
            throw new ServiceException("短链接生成重复");
        }
        // 将新的完整短链接加入到布隆过滤器中
        shortUriCreateCachePenetrationBloomFilter.add("http://" + shortLinkCreateReqDTO.getDomain() + "/" + shortUri);
        // 返回结果
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .originUrl(shortLinkDO.getOriginUrl())
                .gid(shortLinkDO.getGid())
                .build();
    }

    /**
     * 生成短链接后缀
     */
    public String generateSuffix(ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        int maxCustomGenerateCount = 0;
        String shortUri;
        while(true){
            // 失败重试的最大次数
            if(maxCustomGenerateCount > 10){
                throw new ServiceException("短链接生成频繁，请稍后重试！！！");
            }
            // 生成短链接后缀，加上当前的毫秒数，可以防止每次都生成一样的！！
            shortUri = HashUtil.hashToBase62(shortLinkCreateReqDTO.getOriginUrl() + System.currentTimeMillis());
            // 检测完整短链接是否重复生成
            if(!shortUriCreateCachePenetrationBloomFilter.contains("http://" + shortLinkCreateReqDTO.getDomain() + "/" + shortUri)){
                // 如果没有重复生成，那么直接break
                break;
            }
            // 如果重复生成的话，那么我们就重试，并将重试次数加加
            maxCustomGenerateCount++;
        }
        // 返回合格的短链接后缀
        return shortUri;
    }

    /**
     * 短链接分页展示功能
     */
    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getGid, shortLinkPageReqDTO.getGid())
                .eq(ShortLinkDO::getEnableStatus, 0);
        // tip 这里的参数传递不太懂，课下多看下mp的分页插件
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkPageReqDTO, queryWrapper);
        return resultPage.convert(each -> {
            ShortLinkPageRespDTO result = BeanUtil.toBean(each, ShortLinkPageRespDTO.class);
            result.setDomain("http://" + result.getDomain());
            return result;
        });
    }

    /**
     * 短链接信息修改
     */
    @Override
    public void updateShortLinkInfo(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO){
        // tip : 当前用户可以对某个短链接信息进行修改，说明这个短链接一定是存在的

        // 判断一下是否修改了gid，因为如果修改了gid的话，由于短链接是按照gid进行分表的
        // 因此加入真的修改了的话，我们就要先删除原表的后插入新表的
        if(shortLinkUpdateReqDTO.getOriginGid().equals(shortLinkUpdateReqDTO.getGid())){
            // 如果gid是相等的，说明我们只需要进行更新操作就可以
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, shortLinkUpdateReqDTO.getGid())
                    .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getFullShortUrl())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            // tip : 创建新的对象
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .gid(shortLinkUpdateReqDTO.getGid())
                    .describe(shortLinkUpdateReqDTO.getDescribe())
                    .favicon(shortLinkUpdateReqDTO.getFavicon())
                    .validDateType(shortLinkUpdateReqDTO.getValidDateType())
                    .validDate(shortLinkUpdateReqDTO.getValidDateType() == 0 ? null : shortLinkUpdateReqDTO.getValidDate())
                    .fullShortUrl(shortLinkUpdateReqDTO.getFullShortUrl())
                    .shortUri(shortLinkUpdateReqDTO.getShortUri())
                    .originUrl(shortLinkUpdateReqDTO.getOriginUrl())
                    .build();
            // 更新对象
            baseMapper.update(shortLinkDO,updateWrapper);
        }else{
            // tip : 说明gid也发生了变化，那么我们就要进行先删除后插入的操作了

            // 首先查询出原始的对象
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, shortLinkUpdateReqDTO.getOriginGid())
                    .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getOriginFullShortUrl())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);

            ShortLinkDO originShortLinkDO = baseMapper.selectOne(queryWrapper);
            // tip : 创建新的对象
            ShortLinkDO newShortLinkDO = ShortLinkDO.builder()
                    .domain(originShortLinkDO.getDomain())
                    .shortUri(shortLinkUpdateReqDTO.getShortUri())
                    .fullShortUrl(shortLinkUpdateReqDTO.getFullShortUrl())
                    .originUrl(shortLinkUpdateReqDTO.getOriginUrl())
                    .clickNum(originShortLinkDO.getClickNum())
                    .gid(shortLinkUpdateReqDTO.getGid())
                    .enableStatus(originShortLinkDO.getEnableStatus())
                    .createdType(originShortLinkDO.getCreatedType())
                    .describe(shortLinkUpdateReqDTO.getDescribe())
                    .favicon(shortLinkUpdateReqDTO.getFavicon())
                    .validDateType(shortLinkUpdateReqDTO.getValidDateType())
                    .validDate(shortLinkUpdateReqDTO.getValidDateType() == 0 ? null : shortLinkUpdateReqDTO.getValidDate())
                    .originUrl(shortLinkUpdateReqDTO.getOriginUrl())
                    .build();

            // 删除原始用户
            baseMapper.delete(queryWrapper);
            // 插入新的用户
            baseMapper.insert(newShortLinkDO);
        }
    }

    /**
     * 短链接跳转
     */
    @SneakyThrows
    @Override
    public void restoreUrl(String shortUri, ServletRequest request, ServletResponse response) {
        String serverName = request.getServerName();
        String fullShortUrlWithoutPre = "/" + shortUri;
        String fullShortUrl = "http://" + serverName + "/" + shortUri;
        // tip : 从Redis中获取短链接，如果能够获取到，则可以直接跳转
        String originLink = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_SHORT_LINK_KEY + fullShortUrl);
        if(StrUtil.isNotBlank(originLink)){
            // 统计pv
            shortLinkStats(fullShortUrl,null,fullShortUrlWithoutPre,request,response);
            ((HttpServletResponse) response).sendRedirect(originLink);
            return;
        }

        // tip : 检查布隆过滤器中是否存在
        if(!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)){
            // tip : 重定向到空的页面
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            // 如果布隆过滤其中不存在的话，那说明数据库里面肯定也是没有的，因此直接return
            return;
        }

        // tip : 存在则继续往下走
        String gotoIsNullShortLink = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY + fullShortUrl);
        if(StrUtil.isNotBlank(gotoIsNullShortLink)){
            // tip : 重定向到空的页面
            ((HttpServletResponse) response).sendRedirect("/page/notfound");
            return;
        }

        // tip : 当为空的时候，说明可能出现了Key过期的情况，因此就出现了缓存击穿的问题，要加上分布式锁
        // tip : 定义锁
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_GOTO_SHORT_LINK_KEY + fullShortUrl);
        // tip : 这里是阻塞式上锁，这样防止获取不到的直接返回false
        lock.lock();
        try{
            originLink = stringRedisTemplate.opsForValue().get(RedisKeyConstant.GOTO_SHORT_LINK_KEY + fullShortUrl);
            // tip : 如果不为空的话，那么就说明Key还没有过期
            if(StrUtil.isNotBlank(originLink)){
                shortLinkStats(fullShortUrl,null,fullShortUrlWithoutPre,request,response);
                ((HttpServletResponse) response).sendRedirect(originLink);
                return;
            }
            // -> 进行到这里，说明Redis中的Key是过期了的
            // tip : 查询该url对应的gid
            LambdaQueryWrapper<ShortLinkGotoDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkGotoDO.class)
                    .eq(ShortLinkGotoDO::getFullShortUrl, fullShortUrl);
            ShortLinkGotoDO shortLinkGotoDO = shortLinkGotoMapper.selectOne(queryWrapper);
            if(shortLinkGotoDO == null){
                // 说明数据库中不存在
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY + fullShortUrl,"-",30, TimeUnit.MINUTES);
                // tip : 重定向到空的页面
                ((HttpServletResponse) response).sendRedirect("/page/notfound");
                return;
            }

            LambdaQueryWrapper<ShortLinkDO> wrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getGid, shortLinkGotoDO.getGid())
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl)
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(wrapper);
            if(shortLinkDO != null){
                // 判断当前的短链接是不是已经过期了
                if(shortLinkDO.getValidDate() != null && shortLinkDO.getValidDate().before(new Date())){
                    // 过期了和数据库中不存在应当是一个待遇的，我们都应该给缓存中设置空值
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY + fullShortUrl,"-",30, TimeUnit.MINUTES);
                    // tip : 重定向到空的页面
                    ((HttpServletResponse) response).sendRedirect("/page/notfound");
                    return;
                }

                // tip 将数据加载到缓存中
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.GOTO_SHORT_LINK_KEY + fullShortUrl,
                        shortLinkDO.getOriginUrl(),
                        getLinkCacheValidDate(shortLinkDO.getValidDate()),
                        TimeUnit.MILLISECONDS);
                // tip : 进行短链接跳转 + pv统计
                shortLinkStats(fullShortUrl,shortLinkDO.getGid(),fullShortUrlWithoutPre,request,response);
                ((HttpServletResponse) response).sendRedirect(shortLinkDO.getOriginUrl());
            }else{
                ((HttpServletResponse) response).sendRedirect("/page/notfound");
            }
        }finally {
            // 释放锁
            lock.unlock();
        }
    }

    /**
     * 短链接统计
     */
    public void shortLinkStats(String fullShortUrl, String gid, String fullShortUrlWithoutPre,ServletRequest request, ServletResponse response){
        // tip : uv统计
        // todo : 这里不懂为什么要用原子类的布尔，后续要再看看
        AtomicBoolean uvExistFlag = new AtomicBoolean();
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        AtomicReference<String> uv = new AtomicReference<>();
        Runnable addCookie = ()->{
            uv.set(UUID.fastUUID().toString());
            Cookie uvCookie = new Cookie("uv", uv.get());
            uvCookie.setMaxAge(60 * 60 * 24 * 30);
            // tip : Cookie的Path属性决定了哪些路径下的页面可以访问该Cookie
            uvCookie.setPath(StrUtil.sub(fullShortUrl,fullShortUrlWithoutPre.indexOf("/"),fullShortUrl.length()));
            ((HttpServletResponse)response).addCookie(uvCookie);
            // tip : 新增Cookie，一定要将存在的标志设置为true
            uvExistFlag.set(Boolean.TRUE);
            stringRedisTemplate.opsForSet().add(UV_StORE_KEY + fullShortUrl, uv.get());
        };
        // 如果Cookie不是空
        if(ArrayUtil.isNotEmpty(cookies)){
            Arrays.stream(cookies)
                    .filter(each -> Objects.equals(each.getName(),"uv"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .ifPresentOrElse(item->{
                        uv.set(item);
                        // 如果Cookie中存在uv字段，那么检查是否在存储在redis中
                        Long uvAdded = stringRedisTemplate.opsForSet().add(UV_StORE_KEY + fullShortUrl, item);
                        // 如果uvAdded大于0，表示元素成功添加到集合中
                        uvExistFlag.set(uvAdded != null && uvAdded > 0L);
                    },addCookie);
        }else{
            // 如果Cookie是空的，也说明是第一次访问
            addCookie.run();
        }

        // tip : uip统计
        String actualIp = LinkUtil.getActualIp((HttpServletRequest) request);
        Long uipAdded = stringRedisTemplate.opsForSet().add(UIP_STORE_KEY + fullShortUrl, actualIp);
        boolean uipExistFlag = uipAdded != null && uipAdded > 0L;

        // tip : pv统计
        if(StrUtil.isBlank(gid)){
            LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO shortLinkDO = baseMapper.selectOne(queryWrapper);
            gid = shortLinkDO.getGid();
        }
        int hour = DateUtil.hour(new Date(), true);
        int weekday = DateUtil.dayOfWeekEnum(new Date()).getIso8601Value();


        // 数据库更新pv/uv/uid等
        LinkAccessStatsDO linkAccessStatsDO = LinkAccessStatsDO.builder()
                .gid(gid)
                .pv(1)
                .uv(uvExistFlag.get() ? 1 : 0)
                .uip(uipExistFlag ? 1 : 0)
                .fullShortUrl(fullShortUrl)
                .date(new Date())
                .hour(hour)
                .weekday(weekday)
                .build();
        linkAccessStatsMapper.shortLinkStats(linkAccessStatsDO);

        // 数据库更新来访地区
        Map<String,Object> localeParamMap = new HashMap<>();
        localeParamMap.put("key",shortLinkStatsAmapKey);
        localeParamMap.put("ip",actualIp);
        String localeResultStr = HttpUtil.get(AMAP_REMOTE_URL, localeParamMap);
        JSONObject localeResultObj = JSON.parseObject(localeResultStr);
        String infocode = localeResultObj.getString("infocode");
        // 如果返回的状态码不是空，并且状态码的值等于10000，那么就说明请求成功
        if(StrUtil.isNotBlank(infocode) && StrUtil.equals(infocode,"10000")){
            String province = localeResultObj.getString("province");
            boolean unknownFlag = StrUtil.equals(province,"[]");
            LinkLocaleStatsDO linkLocaleStatsDO = LinkLocaleStatsDO.builder()
                    .fullShortUrl(fullShortUrl)
                    .gid(gid)
                    .date(new Date())
                    .cnt(1)
                    .province(unknownFlag ? "未知" : province)
                    .city(unknownFlag ? "未知" : localeResultObj.getString("city"))
                    .adcode(unknownFlag ? "未知" : localeResultObj.getString("adcode"))
                    .country("中国")
                    .build();
            linkLocaleStatsMapper.shortLinkLocaleStats(linkLocaleStatsDO);
        }

        // 统计来访操作系统
        String os = LinkUtil.getOs((HttpServletRequest) request);
        LinkOsStatsDO linkOsStatsDO = LinkOsStatsDO.builder()
                .fullShortUrl(fullShortUrl)
                .gid(gid)
                .date(new Date())
                .cnt(1)
                .os(os)
                .build();
        linkOsStatsMapper.shortLinkOsStats(linkOsStatsDO);

        // 统计来访浏览器
        LinkBrowserStatsDO linkBrowserStatsDO = LinkBrowserStatsDO.builder()
                .browser(LinkUtil.getBrowser(((HttpServletRequest) request)))
                .cnt(1)
                .gid(gid)
                .fullShortUrl(fullShortUrl)
                .date(new Date())
                .build();
        linkBrowserStatsMapper.shortLinkBrowserState(linkBrowserStatsDO);

        // 统计短链接高频访问ip
        LinkAccessLogsDO linkAccessLogsDO = LinkAccessLogsDO.builder()
                .ip(actualIp)
                .fullShortUrl(fullShortUrl)
                .gid(gid)
                .browser(LinkUtil.getBrowser(((HttpServletRequest) request)))
                .os(os)
                .user(uv.get())
                .build();
        linkAccessLogsMapper.insert(linkAccessLogsDO);

        // 统计访问设备类型
        LinkDeviceStatsDO linkDeviceStatsDO = LinkDeviceStatsDO.builder()
                .device(LinkUtil.getDevice(((HttpServletRequest) request)))
                .cnt(1)
                .gid(gid)
                .fullShortUrl(fullShortUrl)
                .date(new Date())
                .build();
        linkDeviceStatsMapper.shortLinkDeviceState(linkDeviceStatsDO);

        // 统计短链接访问网络类型
        LinkNetworkStatsDO linkNetworkStatsDO = LinkNetworkStatsDO.builder()
                .network(LinkUtil.getNetwork(((HttpServletRequest) request)))
                .cnt(1)
                .gid(gid)
                .fullShortUrl(fullShortUrl)
                .date(new Date())
                .build();
        linkNetworkStatsMapper.shortLinkNetworkState(linkNetworkStatsDO);
    }
}
