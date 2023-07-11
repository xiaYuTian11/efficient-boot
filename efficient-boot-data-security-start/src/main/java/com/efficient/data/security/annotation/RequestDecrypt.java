package com.efficient.data.security.annotation;

import java.lang.annotation.*;

/**
 * 针对请求参数进行解密
 *
 * @author TMW
 * @since 2023/6/8 17:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface RequestDecrypt {


}
