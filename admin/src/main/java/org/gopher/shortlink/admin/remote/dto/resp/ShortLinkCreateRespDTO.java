package org.gopher.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * 短链接创建返回参数
 */
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
