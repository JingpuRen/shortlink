package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.common.convention.result.Results;
import org.gopher.shortlink.admin.dto.req.UserLoginReqDTO;
import org.gopher.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.gopher.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.gopher.shortlink.admin.dto.resp.UserLoginRespDTO;
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
    @PostMapping("/api/short-link/v1/user/{username}")
    // 返回的虽然是Result，但是SpringBoot框架会默认将返回的结果序列化成JSON格式
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO userRespDTO = userService.getUserByUsername(username);
        return Results.success(userRespDTO);
    }

    /**
     * 查询用户姓名是否存在
     * @return 存在返回 true ; 不存在返回 false
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUserName(username));
    }

    /**
     * 新增用户
     * @param userRegisterReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<String> Register(@RequestBody UserRegisterReqDTO userRegisterReqDTO){
        // 如果插入失败，那么在其逻辑方法中就会自动返回
        userService.register(userRegisterReqDTO);
        // 能够执行到这里，说明是插入成功的，因此返回成功就可以
        return Results.success("用户新增成功");
    }

    /**
     * 更新用户信息
     * @param userUpdateReqDTO
     * @return
     */
    @PutMapping("/api/short-link/v1/user")
    public Result<String> Update(@RequestBody UserUpdateReqDTO userUpdateReqDTO){
        userService.updateUser(userUpdateReqDTO);
        return Results.success("用户更新成功");
    }

    /**
     * 用户登录
     * @param userLoginReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/user/login")
    public Result<UserLoginRespDTO> Login(@RequestBody UserLoginReqDTO userLoginReqDTO){
        return Results.success(userService.login(userLoginReqDTO));
    }

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */
    @PostMapping("/api/short-link/v1/user/check-login")
    public Result<String> CheckLogin(@RequestParam("username") String username,@RequestParam("token") String token){
        return Results.success(userService.checkLogin(username,token) ? "用户已登录" : "用户尚未登录");
    }
}
