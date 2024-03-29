package com.efficient.file.model.dto;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author TMW
 * @since 2024/1/29 11:01
 */
@Data
public class FileChunkDTO {
    /**
     * 分块文件
     */
    MultipartFile file;
    /**
     * 文件 md5
     */
    private String md5;
    /**
     * 所属模块
     */
    private String module;
    /**
     * 当前分块序号
     */
    private Integer currChunk;
    /**
     * 分块大小
     */
    private Long chunkSize;

    /**
     * 文件总大小
     */
    private Long totalSize;
    /**
     * 分块总数
     */
    private Integer totalChunk;
    /**
     * 文件名
     */
    private String filename;
    private String remark;
    private String contentType;

    public String getModule() {
        if (StrUtil.isBlank(this.module)) {
            return "";
        }
        return module;
    }

}
