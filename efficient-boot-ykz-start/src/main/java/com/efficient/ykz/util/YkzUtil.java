package com.efficient.ykz.util;

import com.efficient.ykz.model.vo.YkzOrg;

import java.util.*;

/**
 *
 * @author TMW
 * @since 2024/1/11 9:37
 */
public class YkzUtil {

    public static List<YkzOrg> createTree(List<YkzOrg> ykzOrgList,boolean flattenTree) {
        Map<Long, YkzOrg> idToNodeMap = new HashMap<>();
        List<YkzOrg> roots = new ArrayList<>();

        // 将所有节点放入Map，方便查找父节点
        for (YkzOrg node : ykzOrgList) {
            idToNodeMap.put(node.getId(), node);
        }

        // 遍历一次，将节点加入父节点的子节点列表
        for (YkzOrg node : ykzOrgList) {
            Long parentId = node.getParentId();
            if (idToNodeMap.get(parentId) != null) {
                YkzOrg parent = idToNodeMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                }
            } else {
                roots.add(node); // 没有父节点的是根节点
            }
        }

        // 对根节点进行排序
        roots.sort(Comparator.comparing(YkzOrg::getDisplayOrder));
        if(flattenTree){
            List<YkzOrg> result = new LinkedList<>();
            flattenTree(roots, result);
            return result;
        }

        return roots;
    }

    private static void flattenTree(List<YkzOrg> nodes, List<YkzOrg> result) {
        for (YkzOrg node : nodes) {
            result.add(node);
            if (node.getChildren() != null) {
                flattenTree(node.getChildren(), result);
                node.setChildren(null);
            }
        }
    }
}
