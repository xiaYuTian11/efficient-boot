package com.efficient.ykz.model.dto.worknotice;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/12 14:18
 */
@Data
public class YkzWorkNoticeMsgLink {
    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接
     */
    private String messageUrl;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 消息标题，建议100字符以内
     */
    private String title;
    /**
     * 消息描述，建议500字符以内
     */
    private String text;

}
