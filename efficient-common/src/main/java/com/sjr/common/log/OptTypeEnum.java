package com.sjr.common.log;

/**
 * 操作类型
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
public enum OptTypeEnum {

    NULL(0, "0"),
    QUERY(1, "查询"),
    PAGE(2, "查询列表"),
    INSERT(3, "新增"),
    UPDATE(4, "修改"),
    DELETE(5, "删除"),
    EXPORT(6, "下载文件"),
    IMPORT(7, "上传文件"),
    LOGIN(8, "登录"),
    LOGOUT(9, "退出"),
    DOWNLOAD(10, "下载"),
    OTHER(11, "其他"),
    CHECK(12, "审核"),
    SAVE(13, "保存");

    private Integer value;
    private String text;

    private OptTypeEnum(Integer value, String text) {
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
