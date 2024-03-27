package com.efficient.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TMW
 * @since 2024/1/4 15:48
 */
public enum UnitTypeEnum {
    FL("1", "分类"),
    DW("2", "单位"),
    NBJG("3", "内部机构");
    private String code;
    private String name;

    UnitTypeEnum(String code, String name) {
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

    public static Map<String, String> map() {
        Map<String, String> map = new HashMap<>();
        for (UnitTypeEnum value : UnitTypeEnum.values()) {
            map.put(value.code, value.name);
        }
        return map;
    }
}
