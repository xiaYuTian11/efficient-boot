package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/11 15:51
 */
@Data
public class YkzApiContentResult<T> {
    private boolean success;
    private String responseCode;
    private String responseMessage;
    private T data;
}
