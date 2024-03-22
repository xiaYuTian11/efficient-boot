package com.efficient.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.system.model.entity.SysUserPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 用户职位信息 持久层
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Mapper
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    SysUserPost getMainByUserId(@Param("userId") String userId);
}
