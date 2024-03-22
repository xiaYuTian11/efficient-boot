package com.efficient.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.system.api.SysUserPostService;
import com.efficient.system.dao.SysUserPostMapper;
import com.efficient.system.model.converter.SysUserPostConverter;
import com.efficient.system.model.entity.SysUserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户职位信息 服务实现类
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    @Autowired
    private SysUserPostConverter sysUserPostConverter;
    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public SysUserPost getMainByUserId(String userId) {
        return sysUserPostMapper.getMainByUserId(userId);
    }
}

