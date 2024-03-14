package com.efficient.cache.constant;

/**
 * 用户常量
 *
 * @author TMW
 * @date 2021/3/4 17:28
 */
public class CacheConstant {
    /**
     * 用户token缓存名
     */
    public static final String CACHE_TOKEN = "token-cache";
    public static final String CACHE_USER_LOGIN = "login-check-cache";
    public static final String CACHE_USER_MANAGE_ORG_TREE = "login-manage-org-tree-cache";
    public static final String CACHE_TREE = "tree-cache";
    public static final String CACHE_ALL_TREE = "all-tree-cache";
    public static final String CACHE_USER_AUDIT_ORG_TREE = "audit-manage-org-tree-cache";
    public static final String CACHE_KICK_OUT = "kick-out-cache";
    /**
     * 进度条
     */
    public static final String CACHE_PROGRESS_BAR = "progress-bar-cache";
    /**
     * 字典表
     */
    public static final String CACHE_DICT = "dict-cache";
    /**
     * 字典表前缀
     */
    public static final String CACHE_DICT_PREFIX = "dict-prefix-cache";
    /**
     * 缓存系统表及字段集合
     */
    public static final String CACHE_DICT_TABLE_ITEM = "dict-table-item-cache";
    /**
     * 验证码缓存
     */
    public static final String CACHE_AUTH_CODE = "auth-code-cache";
    /**
     * 短时间存活
     */
    public static final Integer CACHE_SHORT_TIME = 60;
    /**
     * 长时间存活
     */
    public static final Integer CACHE_LONG_TIME = 86400;

}
