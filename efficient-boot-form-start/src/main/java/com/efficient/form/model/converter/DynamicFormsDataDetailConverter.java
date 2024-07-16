package com.efficient.form.model.converter;

import com.efficient.form.model.dto.DynamicFormsDataDetailDTO;
import com.efficient.form.model.entity.DynamicFormsDataDetail;
import com.efficient.form.model.vo.DynamicFormsDataDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统动态表单-表单数据-详细 模型转换器
* </p>
*
* @author TMW
* @date 2024-07-12 15:09:51
*/
@Mapper(componentModel = "spring")
public interface DynamicFormsDataDetailConverter {

    DynamicFormsDataDetailConverter INSTANCE = Mappers.getMapper(DynamicFormsDataDetailConverter.class);

    @Mappings({})
    DynamicFormsDataDetail dto2Entity(DynamicFormsDataDetailDTO dto);

    @Mappings({})
    DynamicFormsDataDetailVO entity2Vo(DynamicFormsDataDetail entity);

}
