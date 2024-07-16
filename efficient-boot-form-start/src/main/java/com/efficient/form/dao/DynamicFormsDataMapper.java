package com.efficient.form.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.form.model.dto.DynamicFormsDataListDTO;
import com.efficient.form.model.entity.DynamicFormsData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 系统动态表单-表单数据 持久层
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Mapper
public interface DynamicFormsDataMapper extends BaseMapper<DynamicFormsData> {

    Page<Map<String, Object>> listMap(Page<Map<String, Object>> page, @Param("dto") DynamicFormsDataListDTO dto);
}
