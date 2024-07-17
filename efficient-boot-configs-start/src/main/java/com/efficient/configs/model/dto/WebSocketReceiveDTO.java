package com.efficient.configs.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author TMW
 * @since 2024/7/17 15:01
 */
@Data
public class WebSocketReceiveDTO {
    /**
     * 发送方ID
     */
    private String fromId;
    /**
     * 接收方ID
     */
    private List<String> toIdList;
    /**
     * 接收消息
     */
    private String message;
}
