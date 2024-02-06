package com.efficient.common.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 *
 * @author TMW
 * @since 2024/2/5 10:44
 */
@Slf4j
public class RedissonUtil {
    public static <T, R> R execute(RedissonClient redissonClient, String lockName, T param, Function<T, R> successFunc, Function<T, R> failFun) {

        RLock rLock = redissonClient.getLock(lockName);
        boolean isLocked = false;
        try {
            isLocked = rLock.tryLock(3, 3, TimeUnit.SECONDS);
            if (isLocked) {
                log.info("分布式锁：{},执行successFunc", lockName);
                return successFunc.apply(param);
            } else {
                log.info("分布式锁：{},执行failFun", lockName);
                return failFun.apply(param);
            }
        } catch (Exception e) {
            log.error("获取分布式锁失败：{}", lockName, e);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                log.info("释放分布式锁成功：{}", lockName);
            }
        }
        return null;
    }

    // public static <T, R> R execute(RedissonClient redissonClient, String lockName, T param, Function<T, R> successFunc, Function<T, R> failFun) {
    //
    //     RLock rLock = redissonClient.getLock(lockName);
    //     boolean isLocked = false;
    //
    //     try {
    //         isLocked = rLock.tryLock(3, 3, TimeUnit.SECONDS);
    //     } catch (InterruptedException e) {
    //         log.error("获取分布式锁异常，{}", lockName, e);
    //     }
    //     if (isLocked) {
    //         try {
    //             log.info("分布式锁：{},执行successFunc", lockName);
    //             return successFunc.apply(param);
    //         } catch (Exception e) {
    //             log.error("执行分布式锁代码异常，{}", lockName, e);
    //         } finally {
    //             if (rLock.isHeldByCurrentThread()) {
    //                 rLock.unlock();
    //                 log.info("释放分布式锁成功：{}", lockName);
    //             }
    //         }
    //     } else {
    //         log.info("分布式锁：{},执行failFun", lockName);
    //         return failFun.apply(param);
    //     }
    //
    //     return null;
    // }

}