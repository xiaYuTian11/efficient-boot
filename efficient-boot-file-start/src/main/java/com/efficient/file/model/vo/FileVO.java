package com.efficient.file.model.vo;

import lombok.Data;

/**
 * 文件实体类
 *
 * @author TMW
 * @since 2022/4/26 15:08
 */
@Data
public class FileVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型
     */
    private byte[] fileContent;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 存储方式
     */
    private String storeType;

}
