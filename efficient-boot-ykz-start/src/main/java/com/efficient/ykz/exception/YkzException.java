package com.efficient.ykz.exception;

/**
 * @author TMW
 * @since 2022/9/5 9:28
 */
public class YkzException extends RuntimeException {

    private static final long serialVersionUID = 7789313788265829617L;

    public YkzException(String message) {
        super(message);
    }

    public YkzException(String message, Throwable cause) {
        super(message, cause);
    }
}
