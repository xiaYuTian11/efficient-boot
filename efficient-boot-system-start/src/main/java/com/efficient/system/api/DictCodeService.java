package com.efficient.system.api;

import com.efficient.common.entity.TreeNode;
import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.system.model.dto.DictCodeDTO;
import com.efficient.system.model.dto.DictCodeListDTO;
import com.efficient.system.model.entity.DictCode;
import com.efficient.system.model.vo.DictCodeVO;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Map<String, String> findMapByType(String type);

    /**
     * 初始化字典表
     */
    void init();

    List<TreeNode> findTree(String type);
}
