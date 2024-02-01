package com.efficient.file.model.vo;

import lombok.Data;

import java.util.Set;

/**
 *
 * @author TMW
 * @since 2024/1/29 11:04
 */
@Data
public class FileChunkVO {
    /**
     * 是否跳过上传
     */
    private Boolean skipUpload;

    /**
     * 已上传分片的集合
     */
    private Set<Integer> uploaded;

    public FileChunkVO(Boolean skipUpload) {
        this.skipUpload = skipUpload;
    }

    public FileChunkVO(Boolean skipUpload, Set<Integer> uploaded) {
        this.skipUpload = skipUpload;
        this.uploaded = uploaded;
    }
}
