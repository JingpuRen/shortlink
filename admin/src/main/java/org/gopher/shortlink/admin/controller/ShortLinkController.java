package org.gopher.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.remote.dto.ShortLinkRemoteService;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import org.gopher.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 短链接后管控制层
 */
@RestController
public class ShortLinkController {

    // todo 后续重构为SpringCloud远程调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> CreateShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        return shortLinkRemoteService.createShortLink(shortLinkCreateReqDTO);
    }

    /**
     * 短链接中台分页查询
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> PageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO){
        return shortLinkRemoteService.PageShortLink(shortLinkPageReqDTO);
    }

    /**
     * 短链接信息修改
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<String> UpdateShortLinkInfo(@RequestBody ShortLinkUpdateReqDTO shortLinkUpdateReqDTO){
        shortLinkRemoteService.updateShortLinkInfo(shortLinkUpdateReqDTO);
        return Results.success("短链接信息修改成功");
    }
}
