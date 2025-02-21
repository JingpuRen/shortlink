package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.dto.resp.UserRespDTO;
import org.gopher.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public UserRespDTO getUserByUsername(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }
}
