package com.efficient.ykz.model.vo;

import lombok.Data;

import java.util.List;

/**
 *
 * @author TMW
 * @since 2024/1/11 9:38
 */
@Data
public class YkzOrgTree {
    private Long id;
    private String name;
    private Long parentId;
    private List<YkzOrg> child;
}
