package com.efficient.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.system.api.SysUserService;
import com.efficient.system.dao.SysUserMapper;
import com.efficient.system.model.converter.SysUserConverter;
import com.efficient.system.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserConverter sysUserConverter;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByZwddId(String zwddId) {
        return sysUserMapper.getByZwddId(zwddId);
    }
}
