package com.efficient.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.cache.api.CacheUtil;
import com.efficient.cache.constant.CacheConstant;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.entity.TreeNode;
import com.efficient.common.util.TreeUtil;
import com.efficient.system.api.DictCodeService;
import com.efficient.system.dao.DictCodeMapper;
import com.efficient.system.model.converter.DictCodeConverter;
import com.efficient.system.model.entity.DictCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * efficient_dict_code 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:05:08
 */
@Service
@Slf4j
public class DictCodeServiceImpl extends ServiceImpl<DictCodeMapper, DictCode> implements DictCodeService {

    @Autowired
    private DictCodeConverter dictCodeConverter;
    @Autowired
    private DictCodeMapper dictCodeMapper;
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public List<DictCode> findByType(String type) {
        List<DictCode> list = cacheUtil.get(CacheConstant.CACHE_DICT, type);
        if (CollUtil.isEmpty(list)) {
            LambdaQueryWrapper<DictCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DictCode::getIsEnable, CommonConstant.TRUE_INT);
            queryWrapper.eq(DictCode::getCodeType, type);
            queryWrapper.orderByAsc(DictCode::getSort);
            list = this.list(queryWrapper);
            cacheUtil.put(CacheConstant.CACHE_DICT, type, list);
        }
        return list;
    }

    @Override
    public Map<String, String> findMapByType(String type) {
        List<DictCode> list = this.findByType(type);
        if (CollUtil.isEmpty(list)) {
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(DictCode::getCode, DictCode::getCodeName, (k1, k2) -> k1));
    }

    @Override
    public Map<String, String> findReversalMapByType(String type) {
        Map<String, String> mapByType = this.findMapByType(type);
        return com.efficient.common.util.CollUtil.reverseMap(mapByType);
    }

    @Override
    public void init() {
        LambdaQueryWrapper<DictCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictCode::getIsEnable, CommonConstant.TRUE_INT);
        queryWrapper.orderByAsc(DictCode::getCodeType).orderByAsc(DictCode::getSort);
        List<DictCode> list = this.list(queryWrapper);
        if (CollUtil.isEmpty(list)) {
            return;
        }
        Map<String, List<DictCode>> listMap = list.stream().collect(Collectors.groupingBy(DictCode::getCodeType));
        listMap.forEach((k, v) -> cacheUtil.put(CacheConstant.CACHE_DICT, k, v));
        log.info("load dict success!");
    }

    @Override
    public List<TreeNode> findTree(String type) {
        List<DictCode> list = this.findByType(type);
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<TreeNode> treeNodeList = new ArrayList<>();
        list.forEach(et -> {
            TreeNode treeNode = TreeNode.builder()
                    .id(et.getCode())
                    .code(et.getCode())
                    .parentId(et.getParentCode())
                    .order(et.getSort())
                    .name(et.getCodeName())
                    .isRoot(StrUtil.equals(et.getParentCode(), "-1"))
                    .build();
            treeNodeList.add(treeNode);
        });
        return TreeUtil.createListTree(treeNodeList);
    }
}
