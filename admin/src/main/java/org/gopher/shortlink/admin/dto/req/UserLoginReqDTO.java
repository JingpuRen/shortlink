package org.gopher.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户登录请求接口的参数
 */
@Data
public class UserLoginReqDTO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
