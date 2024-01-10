package com.efficient.ykz.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author TMW
 * @since 2024/1/10 10:10
 */
@Data
public class YkzLabel {
    private Long accountId;
    private Long mobile;
    private List<YkzLabelObject> labelObjectList;
}
