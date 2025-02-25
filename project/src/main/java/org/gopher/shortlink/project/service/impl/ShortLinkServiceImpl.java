package org.gopher.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.project.common.convention.exception.ServiceException;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dao.mapper.ShortLinkMapper;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.gopher.shortlink.project.service.ShortLinkService;
import org.gopher.shortlink.project.util.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO>implements ShortLinkService {

    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    /**
     * 创建短链接
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        // 复制对象属性
        ShortLinkDO shortLinkDO = BeanUtil.toBean(shortLinkCreateReqDTO, ShortLinkDO.class);
        // 生成短链接
        String shortUri = generateSuffix(shortLinkCreateReqDTO);
        shortLinkDO.setShortUri(shortUri);
        shortLinkDO.setEnableStatus(0);
        // tip 生成完整的短链接 ： 域名 + / + 后缀
        shortLinkDO.setFullShortUrl(shortLinkCreateReqDTO.getDomain() + "/" + shortUri);
        // 插入新生成的短链接
        try{
            baseMapper.insert(shortLinkDO);
        }catch (DuplicateKeyException ex){
            // tip 这里主要是对布隆过滤器的误判进行预防，因为如果误判的话，但由于我们有唯一索引，因此数据库会报错的！！
            log.warn("ShortLinkServiceImpl\\createShortLink failed");
            throw new ServiceException("短链接生成重复");
        }
        // 将新的短链接后缀加入到布隆过滤器中
        shortUriCreateCachePenetrationBloomFilter.add(shortUri);
        // 返回结果
        return BeanUtil.toBean(shortLinkDO, ShortLinkCreateRespDTO.class);
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
            // 检测短链接后缀是否重复生成
            if(!shortUriCreateCachePenetrationBloomFilter.contains(shortUri)){
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
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
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
                    .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getFullShortUrl())
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
}
