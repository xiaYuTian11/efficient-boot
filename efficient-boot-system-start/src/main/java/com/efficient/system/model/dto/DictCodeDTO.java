package com.efficient.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * efficient_dict_code DTO
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:08:17
 */
@Data
@ApiModel("efficient_dict_code 请求实体-DictCodeDTO")
public class DictCodeDTO implements Serializable {
    private static final long serialVersionUID = 990500152476352459L;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String codeType;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String code;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String codeName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String shortName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long sort;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer isEnable;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String parentCode;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer codeLevel;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer isLeaf;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String pinYin;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String remark;

}

