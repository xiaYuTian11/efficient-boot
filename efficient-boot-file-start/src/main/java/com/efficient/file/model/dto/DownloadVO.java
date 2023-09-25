package com.efficient.file.model.dto;

import com.efficient.common.validate.Common1Group;
import com.efficient.common.validate.Common2Group;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author TMW
 * @since 2022/8/26 10:26
 */
@Data
public class DownloadVO {
    private String fileName;
    @NotBlank(groups = Common2Group.class,message = "filePath 不能为空")
    private String filePath;
    @NotBlank(groups = Common1Group.class,message = "fileId 不能为空")
    private String fileId;
    private String storeType;
}
