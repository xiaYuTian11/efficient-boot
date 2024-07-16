package com.efficient.form.model.converter;

import com.efficient.form.model.dto.DynamicFormsDataDTO;
import com.efficient.form.model.entity.DynamicFormsData;
import com.efficient.form.model.vo.DynamicFormsDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统动态表单-表单数据 模型转换器
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Mapper(componentModel = "spring")
public interface DynamicFormsDataConverter {

    DynamicFormsDataConverter INSTANCE = Mappers.getMapper(DynamicFormsDataConverter.class);

    @Mappings({})
    DynamicFormsData dto2Entity(DynamicFormsDataDTO dto);

    @Mappings({})
    DynamicFormsDataVO entity2Vo(DynamicFormsData entity);

}
