package com.efficient.file.exception;

/**
 * @author TMW
 * @since 2022/9/5 9:28
 */
public class FileException extends RuntimeException {

    private static final long serialVersionUID = -6092213572546542448L;

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
