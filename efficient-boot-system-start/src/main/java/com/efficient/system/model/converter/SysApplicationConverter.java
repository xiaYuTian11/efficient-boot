package com.efficient.system.model.converter;

import com.efficient.system.model.dto.SysApplicationDTO;
import com.efficient.system.model.entity.SysApplication;
import com.efficient.system.model.vo.SysApplicationVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统第三方应用 模型转换器
* </p>
*
* @author TMW
* @date 2024-04-16 16:47:29
*/
@Mapper(componentModel = "spring")
public interface SysApplicationConverter {

    SysApplicationConverter INSTANCE = Mappers.getMapper(SysApplicationConverter.class);

    @Mappings({})
    SysApplication dto2Entity(SysApplicationDTO dto);

    @Mappings({})
    SysApplicationVO entity2Vo(SysApplication entity);

}
