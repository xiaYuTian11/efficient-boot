package com.efficient.form.model.converter;

import com.efficient.form.model.dto.DynamicFormsFieldsDTO;
import com.efficient.form.model.entity.DynamicFormsFields;
import com.efficient.form.model.vo.DynamicFormsFieldsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统动态表单-字段配置 模型转换器
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Mapper(componentModel = "spring")
public interface DynamicFormsFieldsConverter {

    DynamicFormsFieldsConverter INSTANCE = Mappers.getMapper(DynamicFormsFieldsConverter.class);

    @Mappings({})
    DynamicFormsFields dto2Entity(DynamicFormsFieldsDTO dto);

    @Mappings({})
    DynamicFormsFieldsVO entity2Vo(DynamicFormsFields entity);

}
