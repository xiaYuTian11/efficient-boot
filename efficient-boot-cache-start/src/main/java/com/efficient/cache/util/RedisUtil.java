// package com.efficient.cache.util;
//
// import cn.hutool.core.collection.CollUtil;
// import com.efficient.cache.api.CacheUtil;
// import org.springframework.data.redis.core.RedisTemplate;
//
// import java.time.Duration;
// import java.util.Objects;
// import java.util.Set;
//
// /**
//  * @author TMW
//  * @since 2022/9/2 11:12
//  */
// public class RedisUtil implements CacheUtil {
//
//     private RedisTemplate<String, Object> redisTemplate;
//
//     public void init(RedisTemplate<String, Object> redisTemplate) {
//         this.redisTemplate = redisTemplate;
//     }
//
//     @Override
//     public <T> T get(String cacheName, String key) {
//         return (T) redisTemplate.opsForValue().get(cacheName + key);
//     }
//
//     @Override
//     public void put(String cacheName, String key, Object obj) {
//         redisTemplate.opsForValue().set(cacheName + key, obj);
//     }
//
//     @Override
//     public void refresh(String cacheName, String key, int timeToIdleSeconds) {
//         redisTemplate.expire(cacheName + key, Duration.ofSeconds(timeToIdleSeconds));
//     }
//
//     @Override
//     public int getTimeToIdleSeconds(String cacheName, String key) {
//         Long expire = redisTemplate.opsForValue().getOperations().getExpire(cacheName + key);
//         if (Objects.isNull(expire)) {
//             return -1;
//         }
//         return expire.intValue();
//
//     }
//
//     @Override
//     public void removeCache(String cacheName, String key) {
//         redisTemplate.delete(cacheName + key);
//     }
//
//     @Override
//     public void removeCache(String cacheName) {
//         Set<String> keys = redisTemplate.keys(cacheName + "*");
//         if (CollUtil.isNotEmpty(keys)) {
//             keys.forEach(key -> redisTemplate.delete(key));
//         }
//     }
// }
