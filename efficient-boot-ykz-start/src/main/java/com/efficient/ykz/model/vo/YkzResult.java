package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 *
 */
@Data
public class YkzResult {
    private Integer status;
    private String message;
    private Object data;
    private Boolean success;
}
