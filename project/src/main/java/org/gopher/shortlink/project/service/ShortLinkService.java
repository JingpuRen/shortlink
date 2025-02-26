package org.gopher.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
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

    /**
     * 短链接信息修改
     */
    void updateShortLinkInfo(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO);

    /**
     * 短链接跳转
     * @param shortUri 短链接后缀
     * @param request 请求
     * @param response 响应
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);
}
