package com.efficient.cache.api;

import java.time.Duration;

/**
 * @author TMW
 * @since 2022/9/2 10:16
 */
public interface CacheUtil {
    /**
     * 获取缓存
     */
    <T> T get(String cacheName, String key);

    /**
     * 保存缓存
     */
    void put(String cacheName, String key, Object obj);

    void put(String cacheName, String key, Object obj, int timeToIdleSeconds);

    /**
     * 刷新用户缓存
     *
     * @param cacheName         缓存 key
     * @param key               数据对象key
     * @param timeToIdleSeconds 闲置秒数
     */
    void refresh(String cacheName, String key, int timeToIdleSeconds);

    int getTimeToIdleSeconds(String cacheName, String key);

    /**
     * 移除缓存
     */
    void removeCache(String cacheName, String key);

    /**
     * 移除缓存
     */
    void removeCache(String cacheName);
}
