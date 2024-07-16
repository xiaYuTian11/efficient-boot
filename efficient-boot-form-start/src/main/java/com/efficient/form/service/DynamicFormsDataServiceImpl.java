package com.efficient.form.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.result.Result;
import com.efficient.common.util.JackSonUtil;
import com.efficient.configs.config.DatabaseConfig;
import com.efficient.form.api.DynamicFormsDataDetailService;
import com.efficient.form.api.DynamicFormsDataService;
import com.efficient.form.dao.DynamicFormsDataMapper;
import com.efficient.form.model.converter.DynamicFormsDataConverter;
import com.efficient.form.model.dto.DynamicFormsDataDTO;
import com.efficient.form.model.dto.DynamicFormsDataListDTO;
import com.efficient.form.model.entity.DynamicFormsData;
import com.efficient.form.model.entity.DynamicFormsDataDetail;
import com.efficient.form.model.vo.DynamicFormsDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 系统动态表单-表单数据 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Service
public class DynamicFormsDataServiceImpl extends ServiceImpl<DynamicFormsDataMapper, DynamicFormsData> implements DynamicFormsDataService {

    @Autowired
    private DynamicFormsDataConverter dynamicFormsDataConverter;
    @Autowired
    private DynamicFormsDataMapper dynamicFormsDataMapper;
    @Autowired
    private DynamicFormsDataDetailService dynamicFormsDataDetailService;
    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<DynamicFormsData> save(DynamicFormsDataDTO dto) {
        DynamicFormsData entity = dynamicFormsDataConverter.dto2Entity(dto);
        entity.setRecordData(JackSonUtil.toJson(dto.getDataDetailList()));
        boolean flag = this.save(entity);
        List<DynamicFormsDataDetail> dataDetailList = dto.getDataDetailList();
        dynamicFormsDataDetailService.saveByDataId(entity.getFormsId(), entity.getId(), dataDetailList);
        return Result.ok(entity);
    }

    @Override
    public Result<DynamicFormsDataVO> findById(String id) {
        DynamicFormsData entity = this.getById(id);
        DynamicFormsDataVO vo = dynamicFormsDataConverter.entity2Vo(entity);
        List<DynamicFormsDataDetail> dataDetailList = dynamicFormsDataDetailService.findByDataId(id);
        vo.setDataDetailList(dataDetailList);
        return Result.ok(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> update(DynamicFormsDataDTO dto) {
        DynamicFormsData dynamicFormsData = dynamicFormsDataConverter.dto2Entity(dto);
        dynamicFormsData.setRecordData(JackSonUtil.toJson(dto.getDataDetailList()));
        boolean flag = this.updateById(dynamicFormsData);
        dynamicFormsDataDetailService.saveByDataId(dto.getFormsId(), dto.getId(), dto.getDataDetailList());
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delete(String id) {
        boolean flag = this.removeById(id);
        dynamicFormsDataDetailService.deleteByDataId(id);
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Page<Map<String, Object>> list(DynamicFormsDataListDTO dto) {
        Page<Map<String, Object>> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        dto.setDbType(databaseConfig.getDbType().getDb());
        Page<Map<String, Object>> pageResult = dynamicFormsDataMapper.listMap(page, dto);
        List<Map<String, Object>> records = pageResult.getRecords();
        if (CollUtil.isEmpty(records)) {
            return pageResult;
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        records.forEach(et -> {
            Map<String, Object> map = new HashMap<>();
            map.put("dataId", et.get("data_id"));
            String dataStr = (String) et.get("data");
            Map<String, Object> dataMap = JackSonUtil.toMap(dataStr);
            if (Objects.nonNull(dataMap)) {
                map.putAll(dataMap);
            }
            resultList.add(map);
        });
        pageResult.setRecords(resultList);
        return pageResult;
    }
}
