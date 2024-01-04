package com.efficient.ykz.constant;

import com.efficient.common.result.ResultConstant;

/**
 * @author TMW
 * @since 2022/8/26 10:16
 */
public enum YkzResultEnum implements ResultConstant {
    NOT_GET_TOKEN(9500, "未获取到access_token！");

    private int code;
    private String msg;

    YkzResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
