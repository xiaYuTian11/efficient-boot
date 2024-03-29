package com.efficient.ykz.model.dto.msg;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/12 10:45
 */
@Data
public class YkzSendMsgImage extends YkzSendMsgDetail {

    /**
     * 图片消息-图片medialId
     */
    private String mediaId;
    /**
     * 图片消息-图片文件类型0: jpg1: gif2: png3: bpm
     */
    private String fileType;

}
