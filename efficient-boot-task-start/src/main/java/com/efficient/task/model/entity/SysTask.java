package com.efficient.task.model.entity;

import com.baomidou.mybatisplus.annotation.*;
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
public class SysTask implements Serializable {

    private static final long serialVersionUID = 5053091198324685481L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;
    /**
     * 定时任务code
     */
    @TableField("task_code")
    private String taskCode;
    /**
     * 定时任务描述
     */
    @TableField("task_describe")
    private String taskDescribe;
    /**
     * 定时任务全限定名称
     */
    @TableField("task_class")
    private String taskClass;
    /**
     * 是否启用
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 表达式
     */
    @TableField("cron_expression")
    private String cronExpression;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 定时任务状态
     */
    @TableField("task_status")
    private Integer taskStatus;
}
