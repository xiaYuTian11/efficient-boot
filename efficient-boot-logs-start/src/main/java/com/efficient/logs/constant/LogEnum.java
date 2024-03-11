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
    PAGE(2, "查询列表"),
    INSERT(10, "新增"),
    SAVE(20, "保存"),
    UPDATE(30, "修改"),
    DELETE(40, "删除"),
    DOWNLOAD(50, "下载"),
    UPLOAD(60, "上传"),
    IMPORT(70, "导入"),
    EXPORT(80, "导出"),
    CHECK(90, "审核"),
    ROLLBACK(91, "回滚"),
    LOGIN(100, "登录"),
    LOGOUT(110, "退出");

    private Integer code;
    private String opt;

    private LogEnum(Integer code, String opt) {
        this.code = code;
        this.opt = opt;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
