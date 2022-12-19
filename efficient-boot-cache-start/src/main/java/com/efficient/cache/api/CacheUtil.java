package com.efficient.cache.api;

/**
 * @author TMW
 * @since 2022/9/2 10:16
 */
public interface CacheUtil {
    /**
     * 获取缓存
     */
    public <T> T get(String cacheName, String key);

    /**
     * 保存缓存
     */
    public void put(String cacheName, String key, Object obj);

    /**
     * 刷新用户缓存
     *
     * @param cacheName         缓存 key
     * @param key               数据对象key
     * @param timeToIdleSeconds 闲置秒数
     */
    public void refresh(String cacheName, String key, int timeToIdleSeconds);

    public int getTimeToIdleSeconds(String cacheName, String key);

    /**
     * 移除缓存
     */
    public void removeCache(String cacheName, String key);

    /**
     * 移除缓存
     */
    public void removeCache(String cacheName);
}
