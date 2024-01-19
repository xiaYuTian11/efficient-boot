package com.efficient.ykz.model.converter;

import com.efficient.ykz.model.entity.YkzUserPostDb;
import com.efficient.ykz.model.vo.YkzUserPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * 愉快政用户职位信息 模型转换器
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Mapper(componentModel = "spring")
public interface YkzUserPostConverter {

    YkzUserPostConverter INSTANCE = Mappers.getMapper(YkzUserPostConverter.class);

    @Mappings({})
    YkzUserPostDb ykz2Db(YkzUserPost dto);

}
