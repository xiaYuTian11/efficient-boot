package com.efficient.task.constant;

/**
 * @author TMW
 * @since 2022/8/28 22:19
 */
public enum TaskStatusEnum {
    START(1, "运行"),
    STOP(2, "暂停"),
    REMOVE(3, "停止");

    TaskStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}
