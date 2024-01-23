package com.efficient.task.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务表 实体类
 * </p>
 *
 * @author code generator
 * @date 2022-08-28 18:08:04
 */
@Data
@TableName("efficient_sys_task")
@ApiModel
public class SysTask implements Serializable {

    private static final long serialVersionUID = 5053091198324685481L;

    /**
     * 主键
     */
    @TableId("id")
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 定时任务code
     */
    @TableField("task_code")
    @ApiModelProperty(value = "定时任务code")
    private String taskCode;
    /**
     * 定时任务描述
     */
    @TableField("task_describe")
    @ApiModelProperty(value = "定时任务描述")
    private String taskDescribe;
    /**
     * 定时任务全限定名称
     */
    @TableField("task_class")
    @ApiModelProperty(value = "定时任务全限定名称")
    private String taskClass;
    /**
     * 是否启用
     */
    @TableField("enabled")
    @ApiModelProperty(value = "是否启用")
    private Integer enabled;
    /**
     * 表达式
     */
    @TableField("cron_expression")
    @ApiModelProperty(value = "表达式")
    private String cronExpression;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 定时任务状态
     */
    @TableField("task_status")
    @ApiModelProperty(value = "定时任务状态")
    private Integer taskStatus;
}
