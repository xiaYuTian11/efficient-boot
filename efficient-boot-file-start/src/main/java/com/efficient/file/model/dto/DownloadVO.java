package com.efficient.file.model.dto;

import lombok.Data;

/**
 * @author TMW
 * @since 2022/8/26 10:26
 */
@Data
public class DownloadVO {
    private String fileName;
    private String filePath;
    private String fileId;
    private String storeType;
}
