package com.efficient.ykz.model.dto.worknotice;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/12 14:18
 */
@Data
public class YkzWorkNoticeBackOut {
    /**
     * 消息类型，固定填写：workNotification
     */
    private String msgType = "workNotification";
    /**
     * 撤回应用工作通知的应用标识。请填写：应用标识。一般是appkey的前面两部分
     */
    private String msgApp;
    /**
     * 租户
     */
    private String tenantId;
    /**
     * 消息id
     */
    private String bizMsgId;

}
