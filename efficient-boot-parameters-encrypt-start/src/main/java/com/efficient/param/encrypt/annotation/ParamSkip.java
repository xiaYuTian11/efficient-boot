package com.efficient.param.encrypt.annotation;

/**
 * 跳过加解密
 *
 * @author TMW
 * @since 2023/6/8 17:35
 */
public @interface ParamSkip {
    /**
     * 跳过返回参数解密
     */
    boolean skipEncrypt() default true;

    /**
     * 跳过请求参数解密
     */
    boolean skipDecrypt() default true;

}
