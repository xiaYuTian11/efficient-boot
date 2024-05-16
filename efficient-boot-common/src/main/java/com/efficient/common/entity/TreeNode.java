package com.efficient.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * 本级id
     */
    private String id;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * code
     */
    private String code;
    /**
     * 名称
     */
    private String name;
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
    @JsonIgnore
    private Boolean isRoot;
    /**
     * 子节点
     */
    private List<TreeNode> children;
    /**
     * 补充信息
     */
    private TreeInfo extInfo;
}
