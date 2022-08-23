package com.sjr.common.constant;

/**
 * 进度条状态说明
 *
 * @author TMW
 * @since 2022/6/20 10:59
 */
public enum ProgressStatus {

    RUNNING("1"),
    SUCCESS("2"),
    FAIL("3");
    private String code;

    ProgressStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
