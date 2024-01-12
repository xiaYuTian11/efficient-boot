package com.efficient.ykz.model.dto.worknotice;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/12 14:18
 */
@Data
public class YkzWorkNoticeMsgMarkdown {
    /**
     * 标题，首屏会话透出的展示内容
     */
    private String title;
    /**
     * markdown的文本
     */
    private String text;

}
