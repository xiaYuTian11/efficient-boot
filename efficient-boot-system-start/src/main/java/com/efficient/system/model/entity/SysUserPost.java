package com.efficient.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 用户职位信息 实体类
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Data
@TableName("efficient_sys_user_post")
@ApiModel("用户职位信息")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 6412624917822392339L;

    /**
    *主键
    */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private String id;
    /**
    *用户id
    */
    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;
    /**
    *部门ID
    */
    @ApiModelProperty(value = "部门ID")
    @TableField("dept_id")
    private String deptId;
    /**
    *部门层级码
    */
    @ApiModelProperty(value = "部门层级码")
    @TableField("dept_level_code")
    private String deptLevelCode;
    /**
    *单位ID
    */
    @ApiModelProperty(value = "单位ID")
    @TableField("unit_id")
    private String unitId;
    /**
    *单位层级码
    */
    @ApiModelProperty(value = "单位层级码")
    @TableField("unit_level_code")
    private String unitLevelCode;
    /**
    *权限类型，1-个人，2-部门，3-单位，9-自定义
    */
    @ApiModelProperty(value = "权限类型，1-个人，2-部门，3-单位，9-自定义")
    @TableField("permission_type")
    private String permissionType;
    /**
    *是否主职务
    */
    @ApiModelProperty(value = "是否主职务")
    @TableField("main_job")
    private Integer mainJob;
    /**
    *加入时间
    */
    @ApiModelProperty(value = "加入时间")
    @TableField("join_date")
    private Date joinDate;
    /**
    *排序
    */
    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Long sort;
    /**
    *职务
    */
    @ApiModelProperty(value = "职务")
    @TableField("post_name")
    private String postName;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("create_time")
    private Date createTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("update_time")
    private Date updateTime;
    /**
    *
    */
    @ApiModelProperty(value = "")
    @TableField("is_delete")
    private Integer isDelete;
    /**
    *拉取时间
    */
    @ApiModelProperty(value = "拉取时间")
    @TableField("pull_time")
    private Date pullTime;
}
