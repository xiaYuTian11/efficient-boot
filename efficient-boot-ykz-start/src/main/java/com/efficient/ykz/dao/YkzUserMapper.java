package com.efficient.ykz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.ykz.model.entity.YkzUserDb;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * YKZ 用户信息 持久层
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Mapper
public interface YkzUserMapper extends BaseMapper<YkzUserDb> {

}
