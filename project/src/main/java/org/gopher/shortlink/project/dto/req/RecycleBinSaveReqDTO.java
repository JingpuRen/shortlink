package org.gopher.shortlink.project.dto.req;

import lombok.Data;

/**
 * 回收站保存请求参数
 */
@Data
public class RecycleBinSaveReqDTO {

    /**
     * 分组表示
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
