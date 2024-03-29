package com.efficient.ykz.model.dto.worknotice;

import com.efficient.ykz.constant.YkzSendMsgTypeEnum;
import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/12 14:18
 */
@Data
public class YkzWorkNotice {
    /**
     * 接收者的部门id列表， 接收者是部门id下(包括子部门下)的所有用户，与receiverIds都为空时不发送，最大长度列表跟receiverIds加起来不大于1000
     */
    private String organizationCodes;
    /**
     * 接收人用户ID(accountId)， 多个人时使用半角逗号分隔，与organizationCodes都为空时不发送，最大长度列表跟organizationCodes加起来不大于1000
     */
    private String receiverIds;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 业务消息id，自定义，调用方侧需要保证bizMsgId在对应环境全局唯一，禁止使用重复的bizMsgId。(bizMsgId的长度建议64字节以内)
     */
    private String bizMsgId;
    /**
     * 自定义参数
     */
    private String bizId;
    private String createUserId;
    /**
     * json对象 必须,http://ykz-jrxt-client.bigdatacq.com:18080/document?id=19
     */
    private String msg;
    /**
     * 消息类型
     */
    private YkzSendMsgTypeEnum msgType;
    private YkzWorkNoticeMsgText msgText;
    private YkzWorkNoticeMsgLink msgLink;
    private YkzWorkNoticeMsgMarkdown msgMarkdown;

}
