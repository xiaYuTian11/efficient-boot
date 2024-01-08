package com.efficient.ykz.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TMW
 * @since 2024/1/4 15:43
 */
@Data
public class YkzUserCenterAccessToken implements Serializable {
    private static final long serialVersionUID = -4821862749961725265L;
    /**
     * 应用access_token
     */
    private String accessToken;
    /**
     * 过期时间，单位（秒）
     */
    private Integer expiresIn;
}
