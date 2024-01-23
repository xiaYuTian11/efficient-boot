package com.efficient.file.model.dto;

import com.efficient.common.validate.Common1Group;
import com.efficient.common.validate.Common2Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author TMW
 * @since 2022/8/26 10:26
 */
@Data
@ApiModel("文件下载模型")
public class DownloadVO {
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    @ApiModelProperty(value = "文件全路径")
    @NotBlank(groups = Common2Group.class,message = "filePath 不能为空")
    private String filePath;
    @ApiModelProperty(value = "文件ID")
    @NotBlank(groups = Common1Group.class,message = "fileId 不能为空")
    private String fileId;
    @ApiModelProperty(value = "存储类型")
    private String storeType;
}
