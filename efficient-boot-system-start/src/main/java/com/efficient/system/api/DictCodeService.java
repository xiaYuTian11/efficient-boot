package com.efficient.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.common.entity.TreeNode;
import com.efficient.system.model.entity.DictCode;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:05:08
 */
public interface DictCodeService extends IService<DictCode> {
    /**
     * 详情
     */
    List<DictCode> findByType(String type);

    /**
     * 字典表 map
     *
     * @param type 字典表类型
     * @return
     */
    Map<String, String> findMapByType(String type);

    /**
     * 字典表 反转map
     *
     * @param type 字典表类型
     * @return
     */
    Map<String, String> findReversalMapByType(String type);

    /**
     * 初始化字典表
     */
    void init();

    /**
     * 查找字典表树结构
     *
     * @param type 字典表类型
     * @return
     */
    List<TreeNode> findTree(String type);
}
