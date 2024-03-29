package com.efficient.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机构数据 VO
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Data
@ApiModel("机构数据 返回实体-SysUnitVO")
public class SysUnitVO implements Serializable {

    private static final long serialVersionUID = 4778710515825001699L;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    private String id;
    /**
     * 父级机构ID
     */
    @ApiModelProperty(value = "父级机构ID")
    private String parentId;
    /**
     * 机构全称
     */
    @ApiModelProperty(value = "机构全称")
    private String name;
    /**
     * 机构简称
     */
    @ApiModelProperty(value = "机构简称")
    private String shortName;
    /**
     * 机构层级码
     */
    @ApiModelProperty(value = "机构层级码")
    private String levelCode;
    /**
     * 机构类型
     */
    @ApiModelProperty(value = "机构类型")
    private String unitType;
    /**
     * 同级排序字段
     */
    @ApiModelProperty(value = "同级排序字段")
    private Long sort;
    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址")
    private String address;
    /**
     * 区划代码
     */
    @ApiModelProperty(value = "区划代码")
    private String geocode;
    /**
     * 政务钉钉组织机构code
     */
    @ApiModelProperty(value = "政务钉钉组织机构code")
    private String orgCode;
    /**
     * 父组织机构code
     */
    @ApiModelProperty(value = "父组织机构code")
    private String parentOrgCode;
    /**
     * 统一信用代码
     */
    @ApiModelProperty(value = "统一信用代码")
    private String creditCode;
    /**
     * 区域级别
     */
    @ApiModelProperty(value = "区域级别")
    private String areaLevel;
    /**
     * 单位电话
     */
    @ApiModelProperty(value = "单位电话")
    private String telephone;
    /**
     * 单位负责人userCode
     */
    @ApiModelProperty(value = "单位负责人userCode")
    private String principal;
    /**
     * 单位层级中文
     */
    @ApiModelProperty(value = "单位层级中文")
    private String belong;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 是否启用 1-启用，0-停用
     */
    @ApiModelProperty(value = "是否启用 1-启用，0-停用")
    private Integer isEnable;
    /**
     * 创建时间戳
     */
    @ApiModelProperty(value = "创建时间戳")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String updateUser;
    /**
     * 删除标识 1 表示删除，0 表示未删除
     */
    @ApiModelProperty(value = "删除标识 1 表示删除，0 表示未删除")
    private Integer isDelete;
    /**
     * 拉取时间
     */
    @ApiModelProperty(value = "拉取时间")
    private Date pullTime;
}
