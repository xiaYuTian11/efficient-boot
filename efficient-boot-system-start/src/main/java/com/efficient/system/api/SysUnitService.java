package com.efficient.system.api;

import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.system.model.dto.SysUnitDTO;
import com.efficient.system.model.dto.SysUnitListDTO;
import com.efficient.system.model.entity.SysUnit;
import com.efficient.system.model.vo.SysUnitVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 机构数据 服务Api
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
public interface SysUnitService extends IService<SysUnit> {
    String createLevelCode(String parentId);

    String getBelongById(String id);

    SysUnit getByOrgCode(String organizationCode);

    SysUnit getUnitByDeptId(String deptId);
}
