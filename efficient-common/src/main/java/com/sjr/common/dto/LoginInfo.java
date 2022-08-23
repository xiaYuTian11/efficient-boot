package com.sjr.common.dto;

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
}
