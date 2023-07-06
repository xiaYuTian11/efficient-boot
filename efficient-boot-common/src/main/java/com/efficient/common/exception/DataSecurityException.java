package com.efficient.common.exception;

/**
 * 数据加解密异常
 *
 * @author TMW
 * @since 2023/7/6 11:39
 */
public class DataSecurityException extends RuntimeException {

    public DataSecurityException(Throwable cause) {
        super(cause);
    }

    public DataSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
