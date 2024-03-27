package com.efficient.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author TMW
 * @since 2024/3/4 14:30
 */
@AllArgsConstructor
@Getter
public enum LoginTypeEnum {
    LOGIN(1, "正常登录"),
    YKZ_LOGIN(2, "YKZ 登录"),
    YKZ_TODO_LOGIN(3, "YKZ 待办单点"),
    SSO_LOGIN(9, "单点登录");
    private final int code;

    private final String name;

}
