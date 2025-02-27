package org.gopher.shortlink.project.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static org.gopher.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_DATE;

/**
 * 短链接工具类
 */
public class LinkUtil {
    /**
     * 获取短链接缓存的有效时间
     */
    public static long getLinkCacheValidDate(Date validDate){
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(),each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_DATE);
    }
}
