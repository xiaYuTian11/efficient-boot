package com.efficient.ykz.constant;

/**
 *
 * @author TMW
 * @since 2024/1/12 10:42
 */
public enum YkzSendMsgTypeEnum {
    TEXT("text", "文本"),
    LINK("link", "链接"),
    IMAGE("image", "图片"),
    FILE("previewFile", "文件"),
    MARKDOWN("markdown", "markdown"),
    OA("oa", "oa消息"),
    ACTION_CARD("action_card", "卡片消息");

    private String type;
    private String name;

    YkzSendMsgTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
