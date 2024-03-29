package com.efficient.system.model.converter;

import com.efficient.system.model.dto.DictCodeDTO;
import com.efficient.system.model.entity.DictCode;
import com.efficient.system.model.vo.DictCodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * efficient_dict_code 模型转换器
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:08:17
 */
@Mapper(componentModel = "spring")
public interface DictCodeConverter {

    DictCodeConverter INSTANCE = Mappers.getMapper(DictCodeConverter.class);

    @Mappings({})
    DictCode dto2Entity(DictCodeDTO dto);

    @Mappings({})
    DictCodeVO entity2Vo(DictCode entity);

}
