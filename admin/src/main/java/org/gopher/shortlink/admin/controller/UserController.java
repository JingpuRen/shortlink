package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.dto.resp.UserRespDTO;
import org.gopher.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * @RequiredArgsConstructor 是 Lombok 库提供的一个注解，
 * 它的主要作用是自动生成一个包含所有带有 final 修饰符
 * 或者被 @NonNull 注解标记的字段的构造函数。
 */
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 根据用户姓名查询用户信息
     * @param username
     * @return
     */
    @PostMapping("/api/shortlink/v1/user/{username}")
    // 返回的虽然是Result，但是SpringBoot框架会默认将返回的结果序列化成JSON格式
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO userRespDTO = userService.getUserByUsername(username);
        return Results.success(userRespDTO);
    }

    /**
     * 查询用户姓名是否存在
     * @param username
     * @return
     */
    @GetMapping("/api/shortlink/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUserName(username));
    }


}
