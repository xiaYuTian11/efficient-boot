package com.efficient.system.model.converter;

import com.efficient.system.model.dto.SysConfigDTO;
import com.efficient.system.model.entity.SysConfig;
import com.efficient.system.model.vo.SysConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 系统配置 模型转换器
* </p>
*
* @author TMW
* @date 2024-03-26 10:57:51
*/
@Mapper(componentModel = "spring")
public interface SysConfigConverter {

    SysConfigConverter INSTANCE = Mappers.getMapper(SysConfigConverter.class);

    @Mappings({})
    SysConfig dto2Entity(SysConfigDTO dto);

    @Mappings({})
    SysConfigVO entity2Vo(SysConfig entity);

}
