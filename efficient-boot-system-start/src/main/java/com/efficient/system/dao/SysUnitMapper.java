package com.efficient.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.efficient.system.model.entity.SysUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 机构数据 持久层
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@Mapper
public interface SysUnitMapper extends BaseMapper<SysUnit> {

    SysUnit findLastUnitByLength(@Param("unitLevelCount") Integer unitLevelCount);

    SysUnit findLastUnitByParentId(@Param("parentId") String parentId);

    String getBelongById(@Param("id") String id);

    SysUnit getByOrgCode(@Param("organizationCode") String organizationCode);

    SysUnit getUnitByDeptId(@Param("deptId") String deptId);
}
