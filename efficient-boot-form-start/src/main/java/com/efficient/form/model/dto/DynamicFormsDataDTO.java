package com.efficient.form.model.dto;

import com.efficient.form.model.entity.DynamicFormsDataDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统动态表单-表单数据 DTO
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Data
@ApiModel("系统动态表单-表单数据 请求实体-DynamicFormsDataDTO")
public class DynamicFormsDataDTO implements Serializable {
    private static final long serialVersionUID = 921346697354392919L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 表单主键
     */
    @ApiModelProperty(value = "表单主键")
    private String formsId;
    /**
     * 机构层级
     */
    @ApiModelProperty(value = "机构层级")
    private String orgLevelCode;
    /**
     * 表单数据
     */
    @ApiModelProperty(value = "表单数据")
    private String recordData;
    /**
     * 表单数据详情
     */
    @ApiModelProperty(value = "表单数据详情 ")
    private List<DynamicFormsDataDetail> dataDetailList;

}

