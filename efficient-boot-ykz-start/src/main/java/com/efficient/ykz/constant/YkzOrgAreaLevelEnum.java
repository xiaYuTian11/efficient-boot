package com.efficient.ykz.constant;

import com.efficient.system.constant.UnitTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 区域级别areaLevel
 *
 * @author TMW
 * @since 2024/1/10 11:44
 */

public enum YkzOrgAreaLevelEnum {
    SJBM("1", "市级部门"),
    QX("2", "区县"),
    QXBM("3", "区县部门"),
    ZJ("4", "镇街"),
    WU("5", "无"),
    QXGQ("6", "区县国企"),
    QXYY("7", "区县医院"),
    CUN("8", "村"),
    SQ("9", "社区"),
    SSGQ("10", "市属国企"),
    QXXX("11", "区县学校"),
    XXJRJG("12", "区县金融机构"),
    QXQT("13", "区县其他"),
    SSSYDW("14", "市属事业单位"),
    WG("15", "网格"),
    JCZLZHZX("16", "基层治理指挥中心"),
    JCZLSBK("17", "基层治理四板块"),
    QXSSKS("18", "区县下设科室");
    private String code;
    private String name;

    YkzOrgAreaLevelEnum(String code, String name) {
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
        for (YkzOrgAreaLevelEnum value : YkzOrgAreaLevelEnum.values()) {
            map.put(value.code, value.name);
        }
        return map;
    }
}
