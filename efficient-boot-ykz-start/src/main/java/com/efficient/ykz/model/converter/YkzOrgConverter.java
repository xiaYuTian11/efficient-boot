package com.efficient.ykz.model.converter;

import com.efficient.ykz.model.entity.YkzOrgDb;
import com.efficient.ykz.model.vo.YkzOrg;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * efficient_ykz_org 模型转换器
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Mapper(componentModel = "spring")
public interface YkzOrgConverter {

    YkzOrgConverter INSTANCE = Mappers.getMapper(YkzOrgConverter.class);

    @Mappings({})
    YkzOrgDb ykz2Db(YkzOrg dto);

}
