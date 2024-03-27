package com.efficient.ykz.constant;

import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.Map;

/**
 *任职类型 1主职、2兼职、3挂职、4借调
 * @author TMW
 * @since 2024/1/10 14:18
 */
public enum YkzUserPostEnum {
    MAIN_JOB(1, "主职"),
    JIAN_JOB(2, "兼职"),
    GUA_JOB(3, "挂职"),
    JIE_JOB(4, "借调");
    private Integer code;
    private String name;

    YkzUserPostEnum(Integer code, String name) {
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
    public static Map<Integer, String> map() {
        Map<Integer, String> map = new HashMap<>();
        for (YkzUserPostEnum value : YkzUserPostEnum.values()) {
            map.put(value.code, value.name);
        }
        return map;
    }
}
