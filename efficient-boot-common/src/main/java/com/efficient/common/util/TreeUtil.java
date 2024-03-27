package com.efficient.common.util;

import com.efficient.common.entity.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树形工具类
 *
 * @author TMW
 * @since 2022/4/29 11:29
 */
public class TreeUtil {

    private static final Long LAST_ORDER = 9999L;

    /**
     * 获取树形节点集合
     *
     * @param nodeList 节点集合
     * @return 树形节点集合
     */
    public static List<TreeNode> createListTree(List<TreeNode> nodeList) {
        // 使用Map来存储节点，以id为key，节点为value
        Map<String, TreeNode> nodeMap = new HashMap<>();
        Map<String, List<TreeNode>> childrenMap = new HashMap<>();
        List<TreeNode> rootNodes = new ArrayList<>();

        // 第一次遍历，构建nodeMap和childrenMap
        for (TreeNode node : nodeList) {
            nodeMap.put(node.getId(), node);
            String parentId = node.getParentId();

            // 找出所有的根节点
            if (node.getIsRoot() != null && node.getIsRoot()) {
                rootNodes.add(node);
            } else {
                childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(node);
            }
        }

        rootNodes = rootNodes.stream().sorted(Comparator.comparing(TreeNode::getOrder, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(TreeNode::getId)).collect(Collectors.toList());
        Stack<TreeNode> stack = new Stack<>();
        stack.addAll(rootNodes);
        while (!stack.isEmpty()) {
            TreeNode parentNode = stack.pop();
            List<TreeNode> childrenList = childrenMap.getOrDefault(parentNode.getId(), Collections.emptyList());
            childrenList = childrenList.stream().sorted(Comparator.comparing(TreeNode::getOrder, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(TreeNode::getId)).collect(Collectors.toList());
            parentNode.setChildren(childrenList);
            stack.addAll(childrenList);
        }

        // 对结果列表进行排序
        return rootNodes;
    }

    /**
     * 递归设置节点的子节点
     *
     * @param parentNode  父节点
     * @param childrenMap 子节点映射表
     */
    private static void setChildren(TreeNode parentNode, Map<String, List<TreeNode>> childrenMap) {
        List<TreeNode> childrenList = childrenMap.getOrDefault(parentNode.getId(), Collections.emptyList());
        parentNode.setChildren(childrenList);

        for (TreeNode child : childrenList) {
            setChildren(child, childrenMap);
        }
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
