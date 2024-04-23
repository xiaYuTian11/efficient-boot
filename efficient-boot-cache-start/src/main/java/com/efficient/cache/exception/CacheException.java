package com.efficient.cache.exception;

/**
 * @author TMW
 * @since 2022/9/5 9:28
 */
public class CacheException extends RuntimeException {

    private static final long serialVersionUID = -4296315429936146936L;

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
