package com.efficient.task.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务表 VO
 * </p>
 *
 * @author code generator
 * @date 2022-08-28 18:08:04
 */
@Data
@ApiModel
public class SysTaskVO implements Serializable {

    private static final long serialVersionUID = -4016447993683052933L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 定时任务code
     */
    @ApiModelProperty(value = "定时任务code")
    private String taskCode;
    /**
     * 定时任务描述
     */
    @ApiModelProperty(value = "定时任务描述")
    private String taskDescribe;
    /**
     * 定时任务全限定名称
     */
    @ApiModelProperty(value = "定时任务全限定名称")
    private String taskClass;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Integer enabled;
    /**
     * 表达式
     */
    @ApiModelProperty(value = "表达式")
    private String cronExpression;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
