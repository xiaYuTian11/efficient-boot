package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/4 15:43
 */
@Data
public class YkzUserCenterAccessToken {
    /**
     * 应用access_token
     */
    private String accessToken;
    /**
     * 过期时间，单位（秒）
     */
    private Integer expiresIn;
}
