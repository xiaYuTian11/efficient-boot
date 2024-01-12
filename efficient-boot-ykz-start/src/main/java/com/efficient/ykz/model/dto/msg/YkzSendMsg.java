package com.efficient.ykz.model.dto.msg;

import com.efficient.ykz.constant.YkzSendMsgTypeEnum;
import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/12 10:22
 */
@Data
public class YkzSendMsg {
    /**
     * 消息体
     */
    private String msg;
    /**
     * 发送者用户id
     */
    private String senderId;
    /**
     * 单聊接受者用户id（chatType为1时必填）
     */
    private String receiverId;
    /**
     * 群聊会话id（chatType为2时必填）
     */
    private String chatId;
    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 发起的会话类型（1单聊、2群聊）
     */
    private Integer chatType;
    /**
     * 文件类型
     */
    private YkzSendMsgTypeEnum msgType;
    /**
     * 文本
     */
    private YkzSendMsgText textDetail;
    /**
     * 连接
     */
    private YkzSendMsgLink linkDetail;
    /**
     * 图片
     */
    private YkzSendMsgImage imageDetail;
    /**
     * 文件
     */
    private YkzSendMsgFile fileDetail;
}
