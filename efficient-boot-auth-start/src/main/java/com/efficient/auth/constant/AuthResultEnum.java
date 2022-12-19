package com.efficient.auth.constant;

import com.efficient.common.result.ResultConstant;

/**
 * @author TMW
 * @since 2022/8/26 10:16
 */
public enum AuthResultEnum implements ResultConstant {
    CAPTCHA_NOT_MATCH(9700, "验证码错误！"),
    USER_NOT_EXIST(9701, "用户不存在！"),
    USER_MAX_ONLINE(9702, "此账号现同时有多个账号已登录，已超过同一账号最大允许范围，请稍后在试！"),
    ACCOUNT_LOCK(9703, "用户已被锁定！"),
    ACCOUNT_LOCK_COUNT(9704, "用户名或密码错误，您还有%d次机会，该次数过后用户将被锁定！"),
    REQUEST_PATH_ERROR(9750, "请求路径错误！"),
    NOT_LOGIN(9751, "用户未登录");

    AuthResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
