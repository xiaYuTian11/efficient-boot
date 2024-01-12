package com.efficient.ykz.model.dto;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/12 10:45
 */
@Data
public class YkzSendMsgLink extends YkzSendMsgDetail{

    /**
     * 链接消息-点击跳转的链接
     */
    private String messageUrl;
    /**
     * 链接消息-标题（最多50字符）
     */
    private String title;
    /**
     * 链接消息-描述（最多500字符）
     */
    private String text;
    /**
     * 链接消息-图片的mediaId
     */
    private String picMediaId;
    /**
     * 链接消息-来源地址
     */
    private String sourceUrl;



}
