package org.gopher.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户修改请求实体
 */
@Data
public class UserUpdateReqDTO {
    private String	username;
    private String	password;
    private String	realName;
    private String	phone;
    private String	mail;
}
