package com.efficient.data.security.constant;

/**
 * 加解密生效方式
 *
 * @author TMW
 * @since 2023/6/8 17:32
 */
public enum EnableType {
    /**
     * 不需要添加注解就能生效
     */
    NODE,
    /**
     * 需要注解才能生效
     */
    NEED;

}
