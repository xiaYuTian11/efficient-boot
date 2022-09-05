package com.efficient.task.constant;

import com.efficient.common.result.ResultConstant;

/**
 * @author TMW
 * @since 2022/8/26 10:16
 */
public enum TaskResultEnum implements ResultConstant {
    NOT_CHECK_FILE(9700, "定时任务不存在！");

    TaskResultEnum(int code, String msg) {
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
