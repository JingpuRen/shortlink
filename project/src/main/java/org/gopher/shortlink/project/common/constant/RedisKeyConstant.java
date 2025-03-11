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
     * 短链接空值跳转前缀 Key
     */
    public static final String GOTO_IS_NULL_SHORT_LINK_KEY = "short-link:goto:is-null:";

    /**
     * 短链接跳转锁前缀 Key
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short-link:lock:goto:";

    /**
     * 短链接uv存储前缀 Key
     */
    public static final String UV_StORE_KEY = "short-link:uv:store:";
}
