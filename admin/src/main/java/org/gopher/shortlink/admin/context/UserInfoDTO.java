package org.gopher.shortlink.admin.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
}
