package org.gopher.shortlink.project.common.constant;

/**
 * Redis Key常量类
 */
public class RedisKeyConstant {
    /**
     * 短链接跳转前缀 Key
     */
    public static final String GOTO_SHORT_LINK_KEY = "short-link:goto:";

    /**
     * 短链接跳转锁前缀
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short-link:lock:goto:";
}
