package com.efficient.ykz.model.dto.msg;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/12 10:45
 */
@Data
public class YkzSendMsgFile extends YkzSendMsgDetail {

    /**
     * 文件medialId
     */
    private String mediaId;
    /**
     * 文件大小
     */
    private int size;
    /**
     * 文件名-带文件类型
     */
    private int name;
    /**
     * 文件类型（比如docx）
     */
    private int fileType;

}
