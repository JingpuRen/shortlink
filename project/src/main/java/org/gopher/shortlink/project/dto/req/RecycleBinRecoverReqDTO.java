package org.gopher.shortlink.project.dto.req;

import lombok.Data;

@Data
public class RecycleBinRecoverReqDTO {
    /**
     * 分组表示
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
