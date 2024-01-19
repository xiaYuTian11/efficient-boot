package com.efficient.ykz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * efficient_ykz_org 实体类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Data
@TableName("efficient_ykz_org")
public class YkzOrgDb implements Serializable {

    private static final long serialVersionUID = 8164438238825920912L;
    /**
     *主键
     */
    @TableId(value = "id")
    private String id;
    /**
     *机构id
     */
    @TableField(value = "ykz_id")
    private Long ykzId;
    /**
     *机构全称
     */
    @TableField("name")
    private String name;
    /**
     *机构类型
     */
    @TableField("org_type")
    private String orgType;
    /**
     *机构父级id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     *同级排序字段
     */
    @TableField("display_order")
    private Long displayOrder;
    /**
     *删除标识 1 表示删除，0 表示未删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     *是否启用 1-启用，0-停用
     */
    @TableField("is_enable")
    private Integer isEnable;
    /**
     *创建时间戳
     */
    @TableField("create_time")
    private Long createTime;
    /**
     *单位地址
     */
    @TableField("gov_address")
    private String govAddress;
    /**
     *行政区划Code
     */
    @TableField("gov_division_code")
    private String govDivisionCode;
    /**
     *条线Code列表
     */
    @TableField("gov_business_strip_codes")
    private String govBusinessStripCodes;
    /**
     *机构/单位级别
     */
    @TableField("gov_institution_level_code")
    private String govInstitutionLevelCode;
    /**
     *机构简称
     */
    @TableField("gov_short_name")
    private String govShortName;
    /**
     *政务钉钉组织机构code
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     *父组织机构code
     */
    @TableField("parent_organization_code")
    private String parentOrganizationCode;
    /**
     *单位负责人userCode
     */
    @TableField("principal")
    private String principal;
    /**
     *更新时间
     */
    @TableField("update_time")
    private Long updateTime;
    /**
     *统一信用代码
     */
    @TableField("credit_code")
    private String creditCode;
    /**
     *备注
     */
    @TableField("remark")
    private String remark;
    /**
     *区域级别
     */
    @TableField("area_level")
    private String areaLevel;
    /**
     *错误信息
     */
    @TableField("error_info")
    private String errorInfo;
    /**
     *
     */
    @TableField("pull_time")
    private Date pullTime;
}
