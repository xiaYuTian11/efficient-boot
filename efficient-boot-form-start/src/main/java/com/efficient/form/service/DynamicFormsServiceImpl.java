package com.efficient.form.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.result.Result;
import com.efficient.form.api.DynamicFormsFieldsService;
import com.efficient.form.api.DynamicFormsService;
import com.efficient.form.dao.DynamicFormsMapper;
import com.efficient.form.model.converter.DynamicFormsConverter;
import com.efficient.form.model.converter.DynamicFormsFieldsConverter;
import com.efficient.form.model.dto.DynamicFormsDTO;
import com.efficient.form.model.dto.DynamicFormsListDTO;
import com.efficient.form.model.entity.DynamicForms;
import com.efficient.form.model.entity.DynamicFormsFields;
import com.efficient.form.model.vo.DynamicFormsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统动态表单 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Service
public class DynamicFormsServiceImpl extends ServiceImpl<DynamicFormsMapper, DynamicForms> implements DynamicFormsService {

    @Autowired
    private DynamicFormsConverter dynamicFormsConverter;
    @Autowired
    private DynamicFormsMapper dynamicFormsMapper;
    @Autowired
    private DynamicFormsFieldsService dynamicFormsFieldsService;
    @Autowired
    private DynamicFormsFieldsConverter dynamicFormsFieldsConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<DynamicForms> save(DynamicFormsDTO dto) {
        DynamicForms entity = dynamicFormsConverter.dto2Entity(dto);
        boolean flag = this.save(entity);
        dynamicFormsFieldsService.saveByFormsId(entity.getId(), dto.getFormsFieldsList());
        return Result.ok(entity);
    }

    @Override
    public Result<DynamicFormsVO> findById(String id) {
        DynamicForms entity = this.getById(id);
        DynamicFormsVO vo = dynamicFormsConverter.entity2Vo(entity);
        List<DynamicFormsFields> fieldsList = dynamicFormsFieldsService.findByFormsId(id);
        vo.setFormsFieldsList(fieldsList);
        return Result.ok(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> update(DynamicFormsDTO dto) {
        boolean flag = this.updateById(dynamicFormsConverter.dto2Entity(dto));
        dynamicFormsFieldsService.saveByFormsId(dto.getId(), dto.getFormsFieldsList());
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delete(String id) {
        boolean flag = this.removeById(id);
        dynamicFormsFieldsService.deleteByFormsId(id);
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Page<DynamicForms> list(DynamicFormsListDTO dto) {
        LambdaQueryWrapper<DynamicForms> queryWrapper = new LambdaQueryWrapper<>(DynamicForms.class);
        String name = dto.getName();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like(DynamicForms::getName, name);
        }
        Integer isEnabled = dto.getIsEnabled();
        if (Objects.nonNull(isEnabled)) {
            queryWrapper.like(DynamicForms::getIsEnabled, isEnabled);
        }

        queryWrapper.orderByDesc(DynamicForms::getCreateTime);

        final Page<DynamicForms> page = dynamicFormsMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), queryWrapper);
        return page;
    }

    @Override
    public Result<Boolean> changeEnabled(String id, Integer isEnabled) {
        LambdaUpdateWrapper<DynamicForms> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DynamicForms::getId, id);
        updateWrapper.set(DynamicForms::getIsEnabled, isEnabled);
        boolean flag = this.update(updateWrapper);
        return flag ? Result.ok() : Result.fail();
    }
}
