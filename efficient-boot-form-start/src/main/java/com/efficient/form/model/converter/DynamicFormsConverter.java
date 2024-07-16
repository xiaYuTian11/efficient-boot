package com.efficient.form.model.converter;

import com.efficient.form.model.dto.DynamicFormsDTO;
import com.efficient.form.model.entity.DynamicForms;
import com.efficient.form.model.vo.DynamicFormsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统动态表单 模型转换器
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Mapper(componentModel = "spring")
public interface DynamicFormsConverter {

    DynamicFormsConverter INSTANCE = Mappers.getMapper(DynamicFormsConverter.class);

    @Mappings({})
    DynamicForms dto2Entity(DynamicFormsDTO dto);

    @Mappings({})
    DynamicFormsVO entity2Vo(DynamicForms entity);

}
