package com.efficient.cache.constant;

/**
 * 用户常量
 *
 * @author TMW
 * @date 2021/3/4 17:28
 */
public class CacheConstant {
    /**
     * 系统缓存
     */
    public static final String SYSTEM_CACHE = "system-cache";
    public static final String OTHER_CACHE = "other-cache";
    /**
     * 进度条
     */
    public static final String PROGRESS_CACHE = "progress-cache:";
    /**
     * 字典表
     */
    public static final String CACHE_DICT = "dict-cache";
    /**
     * 幂等性缓存
     */
    public static final String RATE_CACHE = "rate-cache";
    /**
     * 短时间存活
     */
    public static final Integer CACHE_SHORT_TIME = 60;
    /**
     * 长时间存活
     */
    public static final Integer CACHE_LONG_TIME = 86400;

}
