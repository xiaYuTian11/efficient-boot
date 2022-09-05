package com.efficient.cache.util;

import com.efficient.cache.api.CacheUtil;
import com.efficient.cache.exception.CacheException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Objects;

/**
 * @author TMW
 * @since 2022/9/2 10:16
 */
public class EhCacheUtil implements CacheUtil {

    private CacheManager cacheManager;

    public void init(CacheManager manager) {
        this.cacheManager = manager;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * 获取缓存
     */
    @Override
    public <T> T get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Element element = cache.get(key);
        if (Objects.isNull(element)) {
            return null;
        }
        return (T) element.getObjectValue();
    }

    /**
     * 保存缓存
     */
    @Override
    public void put(String cacheName, String key, Object obj) {
        final Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new CacheException("cacheName not exist！");
        }
        cache.put(new Element(key, obj));
    }

    /**
     * 刷新用户缓存
     *
     * @param cacheName         缓存 key
     * @param key               数据对象key
     * @param timeToIdleSeconds 闲置秒数
     */
    @Override
    public void refresh(String cacheName, String key, int timeToIdleSeconds) {
        final Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new CacheException("cacheName not exist！");
        }
        cache.get(key).setTimeToIdle(timeToIdleSeconds);
    }

    /**
     * 刷新用户缓存
     *
     * @param cacheName 缓存 key
     * @param key       数据对象key
     */
    @Override
    public int getTimeToIdleSeconds(String cacheName, String key) {
        final Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new CacheException("cacheName not exist！");
        }
        return cache.get(key).getTimeToIdle();
    }

    /**
     * 移除缓存
     */
    @Override
    public void removeCache(String cacheName, String key) {
        final Cache cache = cacheManager.getCache(cacheName);
        if (Objects.nonNull(cache)) {
            cache.remove(key);
        }
    }

    /**
     * 移除缓存
     */
    @Override
    public void removeCache(String cacheName) {
        cacheManager.removeCache(cacheName);
    }
}
