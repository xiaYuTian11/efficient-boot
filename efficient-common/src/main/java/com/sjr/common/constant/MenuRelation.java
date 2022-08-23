package com.sjr.common.constant;

/**
 * @author TMW
 * @since 2021/4/9 11:18
 */
public enum MenuRelation {
    /**
     * 或
     */
    OR(1, "or"),
    /**
     * 且
     */
    AND(2, "and");
    private Integer code;
    private String name;

    MenuRelation(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
