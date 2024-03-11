package com.efficient.file.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.model.entity.SysFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public SysFileInfo findByPathFirst(String destFile) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFilePath, destFile);
        queryWrapper.orderByAsc(SysFileInfo::getId);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        return fileInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public SysFileInfo findByPathAndMd5(String destFile, String md5) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFilePath, destFile);
        queryWrapper.eq(SysFileInfo::getMd5, md5);
        queryWrapper.orderByAsc(SysFileInfo::getId);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        return fileInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean saveListByBizId(List<String> fileIdList, String bizId) {
        if (CollUtil.isEmpty(fileIdList) || StrUtil.isBlank(bizId)) {
            return false;
        }
        fileInfoMapper.deleteByFIleIdListAndBizId(fileIdList, bizId);
        fileInfoMapper.setBizIdWithFileIdList(fileIdList, bizId);
        return true;
    }

    @Override
    public List<SysFileInfo> findByBizId(String bizId) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>(SysFileInfo.class);
        queryWrapper.select(SysFileInfo::getId, SysFileInfo::getBizId, SysFileInfo::getFileName, SysFileInfo::getFileSize, SysFileInfo::getRemark);
        queryWrapper.eq(SysFileInfo::getBizId, bizId);
        queryWrapper.orderByAsc(SysFileInfo::getCreateTime);
        return this.list(queryWrapper);
    }

    @Override
    public List<SysFileInfo> findByBizIdList(List<String> bizIdList) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>(SysFileInfo.class);
        queryWrapper.select(SysFileInfo::getId, SysFileInfo::getBizId, SysFileInfo::getFileName, SysFileInfo::getFileSize, SysFileInfo::getRemark);
        queryWrapper.in(SysFileInfo::getBizId, bizIdList);
        queryWrapper.orderByAsc(SysFileInfo::getCreateTime);
        return this.list(queryWrapper);
    }

    @Override
    public void deleteByBizId(String bizId) {
        fileInfoMapper.deleteByBizId(bizId);
    }

    @Override
    public List<SysFileInfo> findAllByPath(String filePath) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileInfo::getFilePath, filePath);
        return fileInfoMapper.selectList(queryWrapper);
    }
}
