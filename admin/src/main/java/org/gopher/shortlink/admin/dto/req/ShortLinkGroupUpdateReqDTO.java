package org.gopher.shortlink.admin.dto.req;

import lombok.Data;

@Data
public class ShortLinkGroupUpdateReqDTO {
    /**
     * 分组表示
     */
    private String gid;
    /**
     * 分组名称
     */
    private String name;
}
