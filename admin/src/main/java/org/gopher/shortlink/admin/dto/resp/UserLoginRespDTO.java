package org.gopher.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 用户登录接口的返回参数
 */
@Data
public class UserLoginRespDTO {

    /**
     * 用户登录的token
     */
    private String token;
}