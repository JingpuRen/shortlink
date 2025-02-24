package org.gopher.shortlink.project.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 短链接分页返回参数
 */
@Data
public class ShortLinkPageRespDTO {
    // todo 监控数据，目前先不返回
    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

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

    /**
     * 有效期类型 0：永久有效 1：用户自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 创建时间
     */
    // tip : 加这个注解让我们以标准的形式返回
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 描述
     */
    // 如果和MySQL中的关键字出现冲突，那么我们就可以这样子做！
    @TableField("`describe`")
    private String describe;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
