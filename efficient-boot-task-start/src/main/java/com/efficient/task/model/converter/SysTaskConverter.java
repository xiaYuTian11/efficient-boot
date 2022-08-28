package com.efficient.task.model.converter;

import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* <p>
* 定时任务表 模型转换器
* </p>
*
* @author code generator
* @date 2022-08-28 18:08:04
*/
@Mapper(componentModel = "spring")
public interface SysTaskConverter {

    SysTaskConverter INSTANCE = Mappers.getMapper(SysTaskConverter.class);

    @Mappings({})
    SysTask dto2Entity(SysTaskDTO dto);

    @Mappings({})
    SysTaskVO entity2Vo(SysTask entity);

}
