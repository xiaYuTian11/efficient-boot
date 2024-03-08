package com.efficient.common.exception;

/**
 * 数据加解密异常
 *
 * @author TMW
 * @since 2023/7/6 11:39
 */
public class DataSecurityException extends RuntimeException {

    private static final long serialVersionUID = -4393574737559253141L;

    public DataSecurityException(Throwable cause) {
        super(cause);
    }

    public DataSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
