package com.efficient.auth.model.dto;

import com.efficient.auth.constant.LoginTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录参数
 *
 * @author TMW
 * @since 2022/4/28 11:27
 */
@Data
public class LoginInfo {
    @NotBlank(message = "account 不能为空")
    private String account;
    @NotBlank(message = "password 不能为空")
    private String password;
    private String loginIp;
    private String authCode;
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
