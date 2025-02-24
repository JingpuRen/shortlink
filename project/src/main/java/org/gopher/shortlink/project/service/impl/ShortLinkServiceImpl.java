package org.gopher.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.project.common.convention.exception.ServiceException;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dao.mapper.ShortLinkMapper;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
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

}
