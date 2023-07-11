package com.efficient.data.security.annotation;

import java.lang.annotation.*;

/**
 * 跳过加解密
 *
 * @author TMW
 * @since 2023/6/8 17:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface SecuritySkip {
    /**
     * 跳过返回参数解密
     */
    boolean skipRequest() default true;

    /**
     * 跳过请求参数解密
     */
    boolean skipResponse() default true;

}
