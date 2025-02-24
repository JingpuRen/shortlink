package org.gopher.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dao.mapper.ShortLinkMapper;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.project.service.ShortLinkService;
import org.gopher.shortlink.project.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO>implements ShortLinkService {

    /**
     * 创建短链接
     */
    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        // 复制对象属性
        ShortLinkDO shortLinkDO = BeanUtil.toBean(shortLinkCreateReqDTO, ShortLinkDO.class);
        // 生成全部的短链接 ： 域名 + / + 后缀
        String suffix = generateSuffix(shortLinkCreateReqDTO);
        shortLinkDO.setShortUri(suffix);
        shortLinkDO.setEnableStatus(0);
        shortLinkDO.setFullShortUrl(shortLinkCreateReqDTO.getDomain() + "/" + suffix);
        // 插入新生成的短链接
        baseMapper.insert(shortLinkDO);

        return BeanUtil.toBean(shortLinkDO, ShortLinkCreateRespDTO.class);
    }

    /**
     * 生成短链接后缀
     */
    public String generateSuffix(ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        return HashUtil.hashToBase62(shortLinkCreateReqDTO.getOriginUrl());
    }

}
