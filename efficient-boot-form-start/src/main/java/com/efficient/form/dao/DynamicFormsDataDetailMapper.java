package com.efficient.form.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.form.model.entity.DynamicFormsDataDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 系统动态表单-表单数据-详细 持久层
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Mapper
public interface DynamicFormsDataDetailMapper extends BaseMapper<DynamicFormsDataDetail> {
    @Delete("delete from efficient_dynamic_forms_data_detail where  data_id = #{dataId};")
    void deleteByDataId(@Param("dataId") String dataId);
}
