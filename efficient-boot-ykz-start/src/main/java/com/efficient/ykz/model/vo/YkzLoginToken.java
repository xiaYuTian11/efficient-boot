package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/11 16:14
 */
@Data
public class YkzLoginToken {
    /**
     * 登录态access_token
     */
    private String access_token;
    /**
     * access_token过期时间，单位（秒）
     */
    private Long expires_in;
    /**
     * 用于刷新access_token
     */
    private String refresh_token;
    /**
     * token 类型，固定值Bearer
     */
    private String token_type;

}
