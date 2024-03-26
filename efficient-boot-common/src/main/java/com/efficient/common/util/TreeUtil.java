package com.efficient.common.util;

import cn.hutool.core.util.StrUtil;
import com.efficient.common.entity.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 树形工具类
 *
 * @author TMW
 * @since 2022/4/29 11:29
 */
public class TreeUtil {

    public static final String[] NXS_STR = {"a", "b", "c", "d"};
    private static final Long LAST_ORDER = 9999L;

    /**
     * 获取书节点集合
     * 请先设置顶级节点
     * @param nodeList 节点集合
     * @return 树
     */
    public static List<TreeNode> createListTree(List<TreeNode> nodeList) {
        List<TreeNode> rootNodes = nodeList.stream().filter(node -> Objects.nonNull(node.getIsRoot()) && node.getIsRoot()).collect(Collectors.toList());
        List<TreeNode> resultList = new ArrayList<>(4);
        for (TreeNode rootNode : rootNodes) {
            if (Objects.isNull(rootNode.getOrder())) {
                rootNode.setOrder(LAST_ORDER);
            }
            createChildren(rootNode, nodeList);
            resultList.add(rootNode);
        }
        return resultList.stream().sorted(Comparator.comparing(TreeNode::getOrder, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(TreeNode::getId)).collect(Collectors.toList());
    }

    /**
     * 递归构建 子节点
     *
     * @param parentNode 父节点
     * @param nodeList   节点集合
     * @return 父节点 含下级
     */
    public static TreeNode createChildren(TreeNode parentNode, List<TreeNode> nodeList) {
        List<TreeNode> childrenList = new ArrayList<>();
        for (TreeNode treeNode : nodeList) {
            if (StrUtil.equals(treeNode.getParentId(), parentNode.getId())) {
                childrenList.add(createChildren(treeNode, nodeList));
            }
        }
        final List<TreeNode> collect = childrenList.stream().sorted(Comparator.comparing(TreeNode::getOrder, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(TreeNode::getId)).collect(Collectors.toList());
        parentNode.setIsLeaf(collect.isEmpty());
        parentNode.setChildren(collect);
        return parentNode;
    }

    /**
     * 获取书节点
     *
     * @param nodeList 节点集合
     * @return 树
     */
    public static TreeNode createTree(List<TreeNode> nodeList) {
        final List<TreeNode> listTree = createListTree(nodeList);
        return listTree.get(0);
    }
}
