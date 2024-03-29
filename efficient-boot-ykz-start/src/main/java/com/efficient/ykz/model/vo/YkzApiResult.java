package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/11 15:50
 */
@Data
public class YkzApiResult<T> {

    private boolean success;
    private YkzApiContentResult<T> content;
}
