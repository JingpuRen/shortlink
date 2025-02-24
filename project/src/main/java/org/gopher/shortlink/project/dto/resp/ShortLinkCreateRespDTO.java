package org.gopher.shortlink.project.dto.resp;

import lombok.Data;

@Data
public class ShortLinkCreateRespDTO {
    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;
}
