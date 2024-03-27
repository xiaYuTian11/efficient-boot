package com.efficient.ykz.model.converter;

import com.efficient.ykz.model.entity.YkzUserDb;
import com.efficient.ykz.model.vo.YkzUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * YKZ 用户信息 模型转换器
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Mapper(componentModel = "spring")
public interface YkzUserConverter {

    YkzUserConverter INSTANCE = Mappers.getMapper(YkzUserConverter.class);

    @Mappings({})
    YkzUserDb ykz2Db(YkzUser dto);

}
