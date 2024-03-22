package com.efficient.system.model.converter;

import com.efficient.system.model.dto.SysUserDTO;
import com.efficient.system.model.entity.SysUser;
import com.efficient.system.model.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 用户信息 模型转换器
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Mapper(componentModel = "spring")
public interface SysUserConverter {

    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    @Mappings({})
    SysUser dto2Entity(SysUserDTO dto);

    @Mappings({})
    SysUserVO entity2Vo(SysUser entity);

}
