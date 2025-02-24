package org.gopher.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;

public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 短链接分页展示
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
