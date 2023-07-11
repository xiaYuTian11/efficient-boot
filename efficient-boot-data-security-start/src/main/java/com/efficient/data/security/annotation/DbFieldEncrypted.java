package com.efficient.data.security.annotation;

import java.lang.annotation.*;

/**
 * 数据库加密
 *
 * @author TMW
 * @since 2023/7/10 15:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbFieldEncrypted {

}
