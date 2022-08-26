package com.efficient.file.constant;

import com.sjr.common.result.ResultConstant;

/**
 * @author TMW
 * @since 2022/8/26 10:16
 */
public enum FileResultEnum implements ResultConstant {
    NOT_CHECK_FILE(9800, "请先选择文件！"),
    FILE_NOT_EXISTS(9800, "文件不存在！");

    FileResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public void setCode(int code) {

    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public void setMsg(String msg) {

    }
}
