package org.gopher.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.project.common.convention.result.Result;
import org.gopher.shortlink.project.common.convention.result.Results;
import org.gopher.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.gopher.shortlink.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;
    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/project/v1/create")
    public Result<ShortLinkCreateRespDTO> CreateShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }

    /**
     * 短链接分页展示功能
     */
    // tip : 使用mp的分页插件时，需要注意到的一点是我们还要传入current和size两个参数
    // tip : current是要展示的第几页，size是每页的数量
    // tip : 使用分页插件之前要记得引入对应的配置类，mp的分页插件是通过拦截器然后增强形成的！！
    @PostMapping("/api/short-link/project/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> PageShortLink(@RequestBody ShortLinkPageReqDTO shortLinkPageReqDTO){
        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }

}
