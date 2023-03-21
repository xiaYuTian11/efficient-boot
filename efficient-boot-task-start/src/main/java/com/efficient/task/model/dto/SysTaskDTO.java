package com.efficient.task.model.dto;

import lombok.Data;

import java.util.Date;

/**
* <p>
* 定时任务表 DTO
* </p>
*
* @author code generator
* @date 2022-08-28 18:08:04
*/
@Data
public class SysTaskDTO {
    private static final long serialVersionUID = 509121398544314432L;

    /**
    *主键
    */
    private String id;
    /**
    *定时任务code
    */
    private String taskCode;
    /**
    *定时任务描述
    */
    private String taskDescribe;
    /**
    *定时任务全限定名称
    */
    private String taskClass;
    /**
    *是否启用
    */
    private Integer enabled;
    /**
    *表达式
    */
    private String cronExpression;
    /**
    *创建时间
    */
    private Date createTime;

}

