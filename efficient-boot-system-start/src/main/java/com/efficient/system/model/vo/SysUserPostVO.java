package com.efficient.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 用户职位信息 VO
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Data
@ApiModel("用户职位信息 返回实体-SysUserPostVO")
public class SysUserPostVO implements Serializable {

    private static final long serialVersionUID = 7919025033396141079L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
    *用户id
    */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
    *部门ID
    */
    @ApiModelProperty(value = "部门ID")
    private String deptId;
    /**
    *部门层级码
    */
    @ApiModelProperty(value = "部门层级码")
    private String deptLevelCode;
    /**
    *单位ID
    */
    @ApiModelProperty(value = "单位ID")
    private String unitId;
    /**
    *单位层级码
    */
    @ApiModelProperty(value = "单位层级码")
    private String unitLevelCode;
    /**
    *权限类型，1-个人，2-部门，3-单位，9-自定义
    */
    @ApiModelProperty(value = "权限类型，1-个人，2-部门，3-单位，9-自定义")
    private String permissionType;
    /**
    *是否主职务
    */
    @ApiModelProperty(value = "是否主职务")
    private Integer mainJob;
    /**
    *加入时间
    */
    @ApiModelProperty(value = "加入时间")
    private Date joinDate;
    /**
    *排序
    */
    @ApiModelProperty(value = "排序")
    private Long sort;
    /**
    *职务
    */
    @ApiModelProperty(value = "职务")
    private String postName;
    /**
    *
    */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    private Integer isDelete;
    /**
    *拉取时间
    */
    @ApiModelProperty(value = "拉取时间")
    private Date pullTime;
}
