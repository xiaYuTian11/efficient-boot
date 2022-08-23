package com.sjr.common.constant;

/**
 * @author TMW
 * @since 2022/5/27 14:43
 */
public enum FileSuffix {
    DOC("doc"),
    DOCX("docx"),
    PDF("pdf"),
    XLS("xls"),
    XLSX("xlsx");
    private String suffix;

    FileSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
