package com.efficient.ykz.model.dto;

import cn.hutool.core.util.IdUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户中心参数体
 */
@Data
@Builder
public class YkzParam implements Serializable {

    private static final long serialVersionUID = 1198014388149858217L;

    private String requestId;
    private Object data;
}
