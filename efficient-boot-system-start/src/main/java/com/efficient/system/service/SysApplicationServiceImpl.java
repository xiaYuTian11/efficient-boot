package com.efficient.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.common.entity.KeyPair;
import com.efficient.common.result.Result;
import com.efficient.common.util.IdUtil;
import com.efficient.common.util.RsaUtil;
import com.efficient.system.api.SysApplicationService;
import com.efficient.system.dao.SysApplicationMapper;
import com.efficient.system.model.converter.SysApplicationConverter;
import com.efficient.system.model.dto.SysApplicationDTO;
import com.efficient.system.model.dto.SysApplicationListDTO;
import com.efficient.system.model.entity.SysApplication;
import com.efficient.system.model.vo.SysApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统第三方应用 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-04-16 16:02:49
 */
@Service
public class SysApplicationServiceImpl extends ServiceImpl<SysApplicationMapper, SysApplication> implements SysApplicationService {

    @Autowired
    private SysApplicationConverter sysApplicationConverter;
    @Autowired
    private SysApplicationMapper sysApplicationMapper;

    @Override
    public Result<SysApplication> save(SysApplicationDTO dto) {
        SysApplication entity = sysApplicationConverter.dto2Entity(dto);
        entity.setAppCode(IdUtil.generateCode());
        KeyPair keyPair = RsaUtil.generateKeyPair();
        entity.setAppKey(keyPair.getPublicKey());
        entity.setAppSecret(keyPair.getPrivateKey());
        boolean flag = this.save(entity);
        return Result.ok(entity);
    }

    @Override
    public Result<Boolean> update(SysApplicationDTO dto) {
        boolean flag = this.updateById(sysApplicationConverter.dto2Entity(dto));
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Result<Boolean> delete(String id) {
        boolean flag = this.removeById(id);
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Page<SysApplication> list(SysApplicationListDTO dto) {
        LambdaQueryWrapper<SysApplication> queryWrapper = new LambdaQueryWrapper<>(SysApplication.class);
        final Page<SysApplication> page = sysApplicationMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), queryWrapper);
        return page;
    }

    @Override
    public Result<SysApplicationVO> findByAppCode(String appCode) {
        LambdaQueryWrapper<SysApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysApplication::getAppCode, appCode);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        SysApplication sysApplication = sysApplicationMapper.selectOne(queryWrapper);
        SysApplicationVO vo = sysApplicationConverter.entity2Vo(sysApplication);
        return Result.ok(vo);
    }
}
