package com.efficient.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* efficient_dict_code VO
* </p>
*
* @author TMW
* @date 2024-03-29 11:08:17
*/
@Data
@ApiModel("efficient_dict_code 返回实体-DictCodeVO")
public class DictCodeVO implements Serializable {

    private static final long serialVersionUID = 6309580881419780445L;

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
