package org.gopher.shortlink.project.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.project.common.convention.result.Result;
import org.gopher.shortlink.project.common.convention.result.Results;
import org.gopher.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import org.gopher.shortlink.project.service.RecycleBinService;
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
}
