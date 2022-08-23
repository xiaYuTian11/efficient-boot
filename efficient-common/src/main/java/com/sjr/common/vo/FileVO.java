package com.sjr.common.vo;

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
     * 文件路径
     */
    private String filePath;
    /**
     * 文件前缀
     */
    private String fileSuffix;
    /**
     * 绝对路径
     */
    private String path;

}
