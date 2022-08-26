package com.efficient.file.service;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.file.api.FileService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.dto.DownloadVO;
import com.efficient.file.entity.SysFileInfo;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.vo.FileVO;
import com.sjr.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 10:51
 */
@Slf4j
public class DbFileServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements FileService {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public FileProperties getProperties() {
        return fileProperties;
    }

    @Override
    public Result upload(MultipartFile file, boolean unique) throws Exception {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.DB.name());
        sysFileInfo.setFileName(file.getOriginalFilename());
        sysFileInfo.setFileContent(file.getBytes());
        sysFileInfo.setFileSize(file.getSize() / 1024);
        sysFileInfo.setCreateTime(new Date());
        this.save(sysFileInfo);

        FileVO fileVO = new FileVO();
        fileVO.setFileName(sysFileInfo.getFileName());
        fileVO.setStoreType(StoreEnum.DB.name());
        fileVO.setFileId(sysFileInfo.getId());
        return Result.ok(fileVO);
    }

    @Override
    public FileVO getFile(DownloadVO downloadVO) {
        SysFileInfo sysFileInfo = this.getById(downloadVO.getFileId());
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        FileVO vo = new FileVO();
        vo.setFileName(sysFileInfo.getFileName());
        vo.setFileContent(sysFileInfo.getFileContent());
        return vo;
    }

    @Override
    public String saveFileInfo(File file) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.DB.name());
        sysFileInfo.setFileName(file.getName());
        sysFileInfo.setFileContent(FileUtil.readBytes(file));
        sysFileInfo.setFileSize(FileUtil.size(file) / 1024);
        sysFileInfo.setCreateTime(new Date());
        final boolean save = this.save(sysFileInfo);
        return sysFileInfo.getId();
    }
}
