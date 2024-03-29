package com.efficient.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统配置 VO
 * </p>
 *
 * @author TMW
 * @date 2024-03-26 10:57:51
 */
@Data
@ApiModel("系统配置 返回实体-SysConfigVO")
public class SysConfigVO implements Serializable {

    private static final long serialVersionUID = 5155811888278977416L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;
    /**
     * 配置
     */
    @ApiModelProperty(value = "配置")
    private String config;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Integer isEnable;
}
