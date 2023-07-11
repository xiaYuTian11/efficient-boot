package com.efficient.data.security.annotation;

import java.lang.annotation.*;

/**
 * 数据库加密,实体类是否启用
 *
 * @author TMW
 * @since 2023/7/10 15:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbEncrypted {
    /**
     * 是否开启加解密和脱敏模式
     */
    boolean value() default true;
}
