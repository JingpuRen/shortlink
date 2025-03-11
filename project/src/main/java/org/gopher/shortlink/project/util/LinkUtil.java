package org.gopher.shortlink.project.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jakarta.servlet.http.HttpServletRequest;

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

    /**
     * 获取用户真实IP
     * @param request 请求
     * @return 用户真实IP
     */
    public static String getActualIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
