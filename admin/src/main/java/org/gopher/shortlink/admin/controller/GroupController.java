package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.dto.req.ShortLinkGroupCreateReqDTO;
import org.gopher.shortlink.admin.dto.resp.ShortLinkGroupQueryRespDTO;
import org.gopher.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<String> CreateNewGroup(@RequestBody ShortLinkGroupCreateReqDTO shortLinkGroupCreateReqDTO){
        groupService.createNewGroup(shortLinkGroupCreateReqDTO.getName());
        return Results.success("短链接分组 : " + shortLinkGroupCreateReqDTO.getName() + "，创建成功");
    }

    /**
     * 查询当前用户的短链接分组
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupQueryRespDTO>> QueryGroup(){
        return Results.success(groupService.queryGroup());
    }
}
