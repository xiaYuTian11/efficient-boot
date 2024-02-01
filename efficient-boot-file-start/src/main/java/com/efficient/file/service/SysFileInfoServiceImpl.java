package com.efficient.file.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.model.entity.SysFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TMW
 * @since 2024/2/1 15:30
 */
@Service
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService {
    @Autowired
    private SysFileInfoMapper fileInfoMapper;

    @Override
    public SysFileInfo findByPath(String destFile) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFilePath, destFile);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        return fileInfoMapper.selectOne(queryWrapper);
    }
}
