package com.efficient.system.model.converter;

import com.efficient.system.model.dto.SysUserPostDTO;
import com.efficient.system.model.entity.SysUserPost;
import com.efficient.system.model.vo.SysUserPostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 用户职位信息 模型转换器
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Mapper(componentModel = "spring")
public interface SysUserPostConverter {

    SysUserPostConverter INSTANCE = Mappers.getMapper(SysUserPostConverter.class);

    @Mappings({})
    SysUserPost dto2Entity(SysUserPostDTO dto);

    @Mappings({})
    SysUserPostVO entity2Vo(SysUserPost entity);

}
