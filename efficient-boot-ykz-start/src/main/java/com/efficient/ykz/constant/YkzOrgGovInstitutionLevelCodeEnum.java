package com.efficient.ykz.constant;

/**
 * 单位级别govInstitutionLevelCode
 *
 * @author TMW
 * @since 2024/1/10 11:44
 */
public enum YkzOrgGovInstitutionLevelCodeEnum {
    DAN_WEI_JI_BIE_ZHENG_GUO_JIA_JI("DAN_WEI_JI_BIE_ZHENG_GUO_JIA_JI", "正国家级"),
    DAN_WEI_JI_BIE_FU_GUO_JIA_JI("DAN_WEI_JI_BIE_FU_GUO_JIA_JI", "副国家级"),
    DAN_WEI_JI_BIE_ZHENG_SHENG_BU_JI("DAN_WEI_JI_BIE_ZHENG_SHENG_BU_JI", "正省部级"),
    DAN_WEI_JI_BIE_FU_SHENG_BU_JI("DAN_WEI_JI_BIE_FU_SHENG_BU_JI", "副省部级"),
    DAN_WEI_JI_BIE_ZHENG_SI_TING_JU_JI("DAN_WEI_JI_BIE_ZHENG_SI_TING_JU_JI", "正司厅局级"),
    DAN_WEI_JI_BIE_FU_SI_TING_JU_JI("DAN_WEI_JI_BIE_FU_SI_TING_JU_JI", "副司厅局级"),
    DAN_WEI_JI_BIE_ZHENG_XIAN_CHU_JI("DAN_WEI_JI_BIE_ZHENG_XIAN_CHU_JI", "正县处级"),
    DAN_WEI_JI_BIE_FU_XIAN_CHU_JI("DAN_WEI_JI_BIE_FU_XIAN_CHU_JI", "副县处级"),
    DAN_WEI_JI_BIE_ZHENG_XIANG_ZHEN_KE_JI("DAN_WEI_JI_BIE_ZHENG_XIANG_ZHEN_KE_JI", "正乡镇科级"),
    DAN_WEI_JI_BIE_FU_XIANG_ZHEN_KE_JI("DAN_WEI_JI_BIE_FU_XIANG_ZHEN_KE_JI", "副乡镇科级"),
    DAN_WEI_JI_BIE_QUN_ZHONG_ZI_ZHI_TUAN_TI("DAN_WEI_JI_BIE_QUN_ZHONG_ZI_ZHI_TUAN_TI", "群众自治团体"),
    DAN_WEI_JI_BIE_QI_TA("DAN_WEI_JI_BIE_QI_TA", "其他");
    private String code;
    private String name;

    YkzOrgGovInstitutionLevelCodeEnum(String code, String name) {
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
