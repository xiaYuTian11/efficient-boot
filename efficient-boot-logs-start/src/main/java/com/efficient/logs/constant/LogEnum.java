package com.efficient.logs.constant;

/**
 * 日志操作类型
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
public enum LogEnum {

    CUSTOM(-1, ""),
    QUERY(1, "查询"),
    INSERT(10, "新增"),
    SAVE(20, "保存"),
    UPDATE(30, "修改"),
    DELETE(40, "删除"),
    DOWNLOAD(50, "下载"),
    UPLOAD(60, "上传"),
    IMPORT(70, "导入"),
    EXPORT(80, "导出"),
    CHECK(90, "审核"),
    LOGIN(100, "登录"),
    LOGOUT(110, "退出");

    private Integer value;
    private String text;

    private LogEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
