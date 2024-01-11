package com.efficient.ykz.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TMW
 * @since 2024/1/4 16:21
 */
@Data
public class YkzOrg implements Serializable {
    private static final long serialVersionUID = 5852677878994741208L;
    /**
     * 机构id
     */
    private Long id;
    /**
     * 机构全称
     */
    private String name;
    /**
     * 机构类型
     */
    private String orgType;
    /**
     * 机构父级id
     */
    private Long parentId;
    /**
     * 同级排序字段
     */
    private Long displayOrder;
    /**
     * 删除标识 1 表示删除，0 表示未删除
     */
    private Integer isDeleted;
    /**
     * 是否启用 1-启用，0-停用
     */
    private Integer isEnable;
    /**
     * 创建时间戳
     */
    private Long createTime;
    /**
     * 单位地址
     */
    private String govAddress;
    /**
     * 行政区划Code
     */
    private String govDivisionCode;
    /**
     *条线Code列表
     */
    private String govBusinessStripCodes;
    /**
     *机构/单位级别
     */
    private String govInstitutionLevelCode;
    /**
     *机构简称
     */
    private String govShortName;
    /**
     *政务钉钉组织机构code
     */
    private String organizationCode;
    /**
     *父组织机构code
     */
    private String parentOrganizationCode;
    /**
     *单位负责人userCode
     */
    private String principal;
    /**
     *更新时间
     */
    private Long updateTime;
    /**
     *统一信用代码
     */
    private String creditCode;
    /**
     *备注
     */
    private String remark;
    /**
     *区域级别
     */
    private String areaLevel;

    private List<YkzOrg> children;
}
