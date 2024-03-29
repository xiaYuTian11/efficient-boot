package com.efficient.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.system.api.SysUnitService;
import com.efficient.system.constant.SystemConstant;
import com.efficient.system.dao.SysUnitMapper;
import com.efficient.system.model.converter.SysUnitConverter;
import com.efficient.system.model.entity.SysUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 机构数据 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Service
@Slf4j
public class SysUnitServiceImpl extends ServiceImpl<SysUnitMapper, SysUnit> implements SysUnitService {

    @Autowired
    private SysUnitConverter sysUnitConverter;
    @Autowired
    private SysUnitMapper sysUnitMapper;

    @Override
    public synchronized String createLevelCode(String parentId) {
        SysUnit sysUnit = this.getById(parentId);
        if (Objects.isNull(sysUnit)) {
            SysUnit sysUnitLast = sysUnitMapper.findLastUnitByLength(SystemConstant.UNIT_LEVEL_COUNT);
            if (Objects.isNull(sysUnitLast)) {
                return SystemConstant.UNIT_TOP_LEVEL;
            } else {
                String levelCode = sysUnitLast.getLevelCode();
                return getNextLevelCode(levelCode);
            }
        } else {
            SysUnit sysUnitLast = sysUnitMapper.findLastUnitByParentId(parentId);
            if (Objects.isNull(sysUnitLast)) {
                return sysUnit.getLevelCode() + SystemConstant.UNIT_SUB_LEVEL;
            } else {
                String levelCode = sysUnitLast.getLevelCode();
                return getNextLevelCode(levelCode);
            }
        }
    }

    private String getNextLevelCode(String levelCode) {
        String substring = levelCode.substring(levelCode.length() - 3);
        int next = Integer.parseInt(substring) + 1;
        String format = String.format("%03d", next);
        return levelCode.substring(0, levelCode.length() - 3) + format;
    }

    @Override
    public String getBelongById(String id) {
        return sysUnitMapper.getBelongById(id);
    }

    @Override
    public SysUnit getByOrgCode(String organizationCode) {
        return sysUnitMapper.getByOrgCode(organizationCode);
    }

    @Override
    public SysUnit getUnitByDeptId(String deptId) {
        return sysUnitMapper.getUnitByDeptId(deptId);
    }
}
