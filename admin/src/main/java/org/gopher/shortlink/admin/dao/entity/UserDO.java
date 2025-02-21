package org.gopher.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户持久层实体
 */
@Data
@TableName("t_user")
public class UserDO {
    private Long	id;
    private String	username;
    private String	password;
    private String	realName;
    private String	phone;
    private String	mail;
    private Long	deletionTime;
    private Date    createTime;
    private Date	updateTime;
    private Integer	delFlag;
}
