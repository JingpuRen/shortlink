package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.dto.req.ShortLinkGroupCreateReqDTO;
import org.gopher.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.gopher.shortlink.admin.dto.resp.ShortLinkGroupQueryRespDTO;
import org.gopher.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<String> CreateNewGroup(@RequestBody ShortLinkGroupCreateReqDTO shortLinkGroupCreateReqDTO){
        groupService.createNewGroup(shortLinkGroupCreateReqDTO.getName());
        return Results.success("短链接分组 : " + shortLinkGroupCreateReqDTO.getName() + "，创建成功");
    }

    /**
     * 查询当前用户的短链接分组
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<ShortLinkGroupQueryRespDTO>> QueryGroup(){
        return Results.success(groupService.queryGroup());
    }

    /**
     * 修改短链接分组，一般只能修改名称
     */
    @PutMapping("/api/short-link/admin/v1/group")
    public Result<String> UpdateGroup(@RequestBody ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO){
        groupService.updateGroup(shortLinkGroupUpdateReqDTO);
        return Results.success("短链接分组名称修改成功");
    }

    /**
     * 删除短链接分组
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public Result<String> DeleteGroup(@RequestParam String gid){
        groupService.deleteGroup(gid);
        return Results.success("短链接分组删除成功");
    }
}
