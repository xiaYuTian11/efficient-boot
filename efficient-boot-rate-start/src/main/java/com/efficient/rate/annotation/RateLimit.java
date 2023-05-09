package com.efficient.rate.annotation;

import java.lang.annotation.*;

/**
 * 幂等性校验
 *
 * @author TMW
 * @since 2023/1/16 16:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 防重复操作过期时间,默认1s
     */
    long expireTime() default 1000;
}
