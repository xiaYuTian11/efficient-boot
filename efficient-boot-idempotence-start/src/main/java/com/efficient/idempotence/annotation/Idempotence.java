package com.efficient.idempotence.annotation;

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
public @interface Idempotence {
    /**
     * 防重复操作过期时间,默认1s
     */
    int expireTime() default 1;
}
