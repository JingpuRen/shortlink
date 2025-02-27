package org.gopher.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.project.common.convention.result.Result;
import org.gopher.shortlink.project.common.convention.result.Results;
import org.gopher.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import org.gopher.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import org.gopher.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.gopher.shortlink.project.service.RecycleBinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    /**
     * 将短链接移到回收站
     */
    @PostMapping("/api/short-link/recycle/v1/save")
    public Result<String> SaveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO){
        recycleBinService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success("回收站保存成功");
    }

    /**
     * 回收站信息分页展示
     */
    @GetMapping("/api/short-link/recycle/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> PageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO){
        return Results.success(recycleBinService.pageShortLink(shortLinkPageReqDTO));
    }


    /**
     * 从回收站还原短链接
     */
    @PostMapping("/api/short-link/recycle/v1/recover")
    public Result<String> RecoverShortLink(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO){
        recycleBinService.recoverShortLink(recycleBinRecoverReqDTO);
        return Results.success("短链接还原成功");
    }

    /**
     * 从回收站移除短链接
     */
    @PostMapping("/api/short-link/recycle/v1/remove")
    public Result<String> RemoveShortLinkFromRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO){
        recycleBinService.removeShortLinkFromRecycleBin(recycleBinRemoveReqDTO);
        return Results.success("从回收站移除短链接成功");
    }
}
