package com.efficient.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.constant.DbConstant;
import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.common.util.JackSonUtil;
import com.efficient.system.api.SysConfigService;
import com.efficient.system.model.converter.SysConfigConverter;
import com.efficient.system.dao.SysConfigMapper;
import com.efficient.system.model.dto.SysConfigDTO;
import com.efficient.system.model.dto.SysConfigListDTO;
import com.efficient.system.model.entity.SysConfig;
import com.efficient.system.model.vo.SysConfigVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-03-26 10:57:51
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigConverter sysConfigConverter;
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public Result<SysConfig> save(SysConfigDTO dto) {
        SysConfig entity = sysConfigConverter.dto2Entity(dto);
        boolean flag = this.save(entity);
        return Result.ok(entity);
    }

    @Override
    public <T> T findByCode(String code, Class<T> tClass) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getCode, code);
        queryWrapper.eq(SysConfig::getIsEnable, CommonConstant.TRUE_INT);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        SysConfig sysConfig = sysConfigMapper.selectOne(queryWrapper);
        if (Objects.isNull(sysConfig)) {
            return null;
        }
        return JackSonUtil.toObject(sysConfig.getConfig(), tClass);
    }
}
