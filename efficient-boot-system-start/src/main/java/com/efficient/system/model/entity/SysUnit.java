package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机构数据 实体类
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Data
@TableName("efficient_sys_unit")
@ApiModel("机构数据")
public class SysUnit implements Serializable {

    private static final long serialVersionUID = 5887731238785928510L;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    @TableId(value = "id")
    private String id;
    /**
     * 父级机构ID
     */
    @ApiModelProperty(value = "父级机构ID")
    @TableField("parent_id")
    private String parentId;
    /**
     * 机构全称
     */
    @ApiModelProperty(value = "机构全称")
    @TableField("name")
    private String name;
    /**
     * 机构简称
     */
    @ApiModelProperty(value = "机构简称")
    @TableField("short_name")
    private String shortName;
    /**
     * 机构层级码
     */
    @ApiModelProperty(value = "机构层级码")
    @TableField("level_code")
    private String levelCode;
    /**
     * 机构类型
     */
    @ApiModelProperty(value = "机构类型,1-分类，2-单位，3-内部机构")
    @TableField("unit_type")
    private String unitType;
    /**
     * 同级排序字段
     */
    @ApiModelProperty(value = "同级排序字段")
    @TableField("sort")
    private Long sort;
    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址")
    @TableField("address")
    private String address;
    /**
     * 区划代码
     */
    @ApiModelProperty(value = "区划代码")
    @TableField("geocode")
    private String geocode;
    /**
     * 政务钉钉组织机构code
     */
    @ApiModelProperty(value = "政务钉钉组织机构code")
    @TableField("org_code")
    private String orgCode;
    /**
     * 父组织机构code
     */
    @ApiModelProperty(value = "父组织机构code")
    @TableField("parent_org_code")
    private String parentOrgCode;
    /**
     * 统一信用代码
     */
    @ApiModelProperty(value = "统一信用代码")
    @TableField("credit_code")
    private String creditCode;
    /**
     * 区域级别
     */
    @ApiModelProperty(value = "区域级别")
    @TableField("area_level")
    private String areaLevel;
    /**
     * 单位电话
     */
    @ApiModelProperty(value = "单位电话")
    @TableField("telephone")
    private String telephone;
    /**
     * 单位负责人userCode
     */
    @ApiModelProperty(value = "单位负责人userCode")
    @TableField("principal")
    private String principal;
    /**
     * 单位层级中文
     */
    @ApiModelProperty(value = "单位层级中文")
    @TableField("belong")
    private String belong;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
    /**
     * 是否启用 1-启用，0-停用
     */
    @ApiModelProperty(value = "是否启用 1-启用，0-停用")
    @TableField("is_enable")
    private Integer isEnable;
    /**
     * 创建时间戳
     */
    @ApiModelProperty(value = "创建时间戳")
    @TableField("create_time")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @TableField("create_user")
    private String createUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @TableField("update_user")
    private String updateUser;
    /**
     * 删除标识 1 表示删除，0 表示未删除
     */
    @ApiModelProperty(value = "删除标识 1 表示删除，0 表示未删除")
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 拉取时间
     */
    @ApiModelProperty(value = "拉取时间")
    @TableField("pull_time")
    private Date pullTime;
}
