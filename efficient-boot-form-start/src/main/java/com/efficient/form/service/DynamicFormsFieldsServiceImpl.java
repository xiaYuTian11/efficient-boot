package com.efficient.form.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.constant.DbConstant;
import com.efficient.form.api.DynamicFormsFieldsService;
import com.efficient.form.dao.DynamicFormsFieldsMapper;
import com.efficient.form.model.converter.DynamicFormsFieldsConverter;
import com.efficient.form.model.entity.DynamicFormsFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统动态表单-字段配置 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Service
public class DynamicFormsFieldsServiceImpl extends ServiceImpl<DynamicFormsFieldsMapper, DynamicFormsFields> implements DynamicFormsFieldsService {

    @Autowired
    private DynamicFormsFieldsConverter dynamicFormsFieldsConverter;
    @Autowired
    private DynamicFormsFieldsMapper dynamicFormsFieldsMapper;

    @Override
    public List<DynamicFormsFields> findByFormsId(String formsId) {
        LambdaQueryWrapper<DynamicFormsFields> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicFormsFields::getFormsId, formsId);
        queryWrapper.orderByAsc(DynamicFormsFields::getSort);
        return this.list(queryWrapper);
    }

    @Override
    public void saveByFormsId(String formsId, List<DynamicFormsFields> formsFieldsList) {
        dynamicFormsFieldsMapper.deleteByFormsId(formsId);
        formsFieldsList.forEach(et->et.setFormsId(formsId));
        this.saveBatch(formsFieldsList, DbConstant.BATCH_SIZE);
    }

    @Override
    public void deleteByFormsId(String formsId) {
        LambdaUpdateWrapper<DynamicFormsFields> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DynamicFormsFields::getFormsId, formsId);
        updateWrapper.set(DynamicFormsFields::getIsDelete, CommonConstant.TRUE_INT);
        this.update(updateWrapper);
    }
}
