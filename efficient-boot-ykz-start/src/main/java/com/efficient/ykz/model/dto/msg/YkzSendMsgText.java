package com.efficient.ykz.model.dto.msg;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/12 10:45
 */
@Data
public class YkzSendMsgText extends YkzSendMsgDetail {

    /**
     * 文本消息内容（最多3000字符）
     */
    private String content;

}
