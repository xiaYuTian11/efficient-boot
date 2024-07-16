package com.efficient.form.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.constant.DbConstant;
import com.efficient.form.api.DynamicFormsDataDetailService;
import com.efficient.form.dao.DynamicFormsDataDetailMapper;
import com.efficient.form.model.converter.DynamicFormsDataDetailConverter;
import com.efficient.form.model.entity.DynamicFormsDataDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统动态表单-表单数据-详细 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Service
public class DynamicFormsDataDetailServiceImpl extends ServiceImpl<DynamicFormsDataDetailMapper, DynamicFormsDataDetail> implements DynamicFormsDataDetailService {

    @Autowired
    private DynamicFormsDataDetailConverter dynamicFormsDataDetailConverter;
    @Autowired
    private DynamicFormsDataDetailMapper dynamicFormsDataDetailMapper;

    @Override
    public void saveByDataId(String formsId, String dataId, List<DynamicFormsDataDetail> dataDetailList) {
        dynamicFormsDataDetailMapper.deleteByDataId(dataId);
        dataDetailList.forEach(et -> {
            et.setDataId(dataId);
            et.setFormsId(formsId);
        });
        this.saveBatch(dataDetailList, DbConstant.BATCH_SIZE);
    }

    @Override
    public void deleteByDataId(String dataId) {
        LambdaUpdateWrapper<DynamicFormsDataDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DynamicFormsDataDetail::getDataId, dataId);
        updateWrapper.set(DynamicFormsDataDetail::getIsDelete, CommonConstant.TRUE_INT);
        this.update(updateWrapper);
    }

    @Override
    public List<DynamicFormsDataDetail> findByDataId(String dataId) {
        LambdaQueryWrapper<DynamicFormsDataDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicFormsDataDetail::getDataId, dataId);
        queryWrapper.orderByAsc(DynamicFormsDataDetail::getId);
        return this.list(queryWrapper);
    }
}
