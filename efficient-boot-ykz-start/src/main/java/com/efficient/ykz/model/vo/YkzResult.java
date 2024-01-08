package com.efficient.ykz.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class YkzResult implements Serializable {
    private static final long serialVersionUID = -8007315875930770014L;
    private Integer status;
    private String message;
    private Object data;
    private Boolean success;
}
