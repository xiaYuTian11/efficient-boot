package com.efficient.ykz.constant;

/**
 * 机构类型orgType
 *
 * @author TMW
 * @since 2024/1/10 11:44
 */

public enum YkzOrgTypeEnum {
    GOV_UNIT("GOV_UNIT", "单位"),
    GOV_INTERNAL_INSTITUTION("GOV_INTERNAL_INSTITUTION", "内设机构"),
    GOV_VIRTUAL("GOV_VIRTUAL", "虚拟组织"),
    GOV_TEMPORARY("GOV_TEMPORARY", "临时组织"),
    GOV_HOLLOW_DIVISION_NODE("GOV_HOLLOW_DIVISION_NODE", "行政区划虚节点"),
    GOV_HOLLOW_STRIP_NODE("GOV_HOLLOW_STRIP_NODE", "条线虚节点");
    private String code;
    private String name;

    YkzOrgTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
