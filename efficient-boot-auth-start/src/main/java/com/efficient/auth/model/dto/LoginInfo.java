package com.efficient.auth.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录参数
 *
 * @author TMW
 * @since 2022/4/28 11:27
 */
@Data
public class LoginInfo {
    private String userId;
    private String account;
    private String password;
    private String loginIp;
    private String authCode;
    /**
     * com.efficient.auth.constant.LoginTypeEnum
     */
    @NotNull(message = "loginType 不能为空")
    private Integer loginType;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 验证码ID
     */
    private String captchaId;
    /**
     * 扩展信息
     */
    private Object extendInfo;
}
