package com.efficient.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.system.model.entity.SysUnit;

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
