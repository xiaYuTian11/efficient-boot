package com.efficient.form.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.form.model.entity.DynamicFormsFields;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统动态表单-字段配置 持久层
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@Mapper
public interface DynamicFormsFieldsMapper extends BaseMapper<DynamicFormsFields> {

    @Delete("delete from efficient_dynamic_forms_fields where forms_id = #{formsId}")
    void deleteByFormsId(@Param("formsId") String formsId);
}
