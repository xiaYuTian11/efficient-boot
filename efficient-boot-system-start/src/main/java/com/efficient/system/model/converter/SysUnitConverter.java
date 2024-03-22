package com.efficient.system.model.converter;

import com.efficient.system.model.dto.SysUnitDTO;
import com.efficient.system.model.entity.SysUnit;
import com.efficient.system.model.vo.SysUnitVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 机构数据 模型转换器
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Mapper(componentModel = "spring")
public interface SysUnitConverter {

    SysUnitConverter INSTANCE = Mappers.getMapper(SysUnitConverter.class);

    @Mappings({})
    SysUnit dto2Entity(SysUnitDTO dto);

    @Mappings({})
    SysUnitVO entity2Vo(SysUnit entity);

}
