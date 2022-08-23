package com.sjr.common.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author TMW
 * @since 2022/4/29 10:24
 */
@Data
@Builder
public class TreeNode {
    /**
     * 本级code
     */
    private String code;
    /**
     * 父级code
     */
    private String parentCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 排序
     */
    private Long order;
    /**
     * 是否叶子节点
     */
    private Boolean isLeaf;
    /**
     * 是否顶级节点
     */
    private Boolean isRoot;
    /**
     * 子节点
     */
    private List<TreeNode> children;
}
