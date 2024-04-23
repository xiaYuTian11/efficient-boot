package com.efficient.file.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.DbConstant;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.constant.FileConstant;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.util.FileMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

import static com.efficient.file.constant.FileConstant.KB;

/**
 * @author TMW
 * @since 2024/2/1 15:30
 */
@Service
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService {
    @Autowired
    private SysFileInfoMapper fileInfoMapper;
    @Autowired
    private FileProperties fileProperties;

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

    @Override
    public SysFileInfo findByBizIdAndRemark(String bizId, String remark) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>(SysFileInfo.class);
        queryWrapper.eq(SysFileInfo::getBizId, bizId);
        queryWrapper.eq(SysFileInfo::getRemark, remark);
        queryWrapper.last(DbConstant.LIMIT_ONE);
        return fileInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public File getDownPath(String fileName) {
        String reName = System.currentTimeMillis() + "_" + fileName;
        String filePath = fileProperties.getLocal().getLocalPath() + FileConstant.DOWNLOAD_LINE + DateUtil.format(new Date(), "/yyyy/MM/dd/") + reName;
        File file = new File(filePath);
        synchronized (this) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    @Override
    public SysFileInfo saveDownFile(File downLoadFile, String bizId, String fileName, String remark) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setBizId(bizId);
        sysFileInfo.setStoreType(StoreEnum.LOCAL.name());
        sysFileInfo.setFileName(fileName);
        sysFileInfo.setFilePath(downLoadFile.getAbsolutePath());
        sysFileInfo.setFileSize(FileUtil.size(downLoadFile) / KB);
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setMd5(FileMd5Util.calculateMD5(downLoadFile));
        sysFileInfo.setRemark(remark);
        sysFileInfo.setContentType(FileUtil.getMimeType(downLoadFile.getPath()));
        this.save(sysFileInfo);
        return sysFileInfo;
    }
}
