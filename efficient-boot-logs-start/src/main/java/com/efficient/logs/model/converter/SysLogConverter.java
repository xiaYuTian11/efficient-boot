package com.efficient.logs.model.converter;

import com.efficient.logs.model.dto.SysLogDTO;
import com.efficient.logs.model.entity.SysLog;
import com.efficient.logs.model.vo.SysLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
*  模型转换器
* </p>
*
* @author code generator
* @date 2022-09-05 16:24:37
*/
@Mapper(componentModel = "spring")
public interface SysLogConverter {

    SysLogConverter INSTANCE = Mappers.getMapper(SysLogConverter.class);

    @Mappings({})
    SysLog dto2Entity(SysLogDTO dto);

    @Mappings({})
    SysLogVO entity2Vo(SysLog entity);

}
