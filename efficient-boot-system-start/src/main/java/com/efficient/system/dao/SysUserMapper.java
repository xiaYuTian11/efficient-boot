package com.efficient.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.system.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息 持久层
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getByZwddId(@Param("zwddId") String zwddId);
}
