package org.gopher.shortlink.admin.controller;

import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.remote.dto.ShortLinkRemoteService;
import org.gopher.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接回收站控制层
 */
@RestController
public class RecycleBinController {

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService(){

    };

    /**
     * 将短链接移到回收站
     */
    @PostMapping("/api/short-link/admin/v1/save")
    public Result<String> SaveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO){
        shortLinkRemoteService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success("回收站保存成功");
    }
}
