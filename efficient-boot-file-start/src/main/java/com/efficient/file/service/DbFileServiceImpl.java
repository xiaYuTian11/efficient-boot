package com.efficient.file.service;

import cn.hutool.core.io.FileUtil;
import com.efficient.common.result.Result;
import com.efficient.file.api.FileService;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import com.efficient.file.properties.FileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 10:51
 */
@Slf4j
public class DbFileServiceImpl implements FileService {

    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private SysFileInfoService fileInfoService;

    @Override
    public Result<FileVO> upload(MultipartFile file, boolean unique, String module, String md5, String remark) throws Exception {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.DB.name());
        sysFileInfo.setFileName(file.getOriginalFilename());
        sysFileInfo.setFileContent(file.getBytes());
        sysFileInfo.setFileSize(file.getSize() / 1024);
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setMd5(md5);
        sysFileInfo.setRemark(remark);
        fileInfoService.save(sysFileInfo);


        FileVO fileVO = new FileVO();
        fileVO.setFileName(sysFileInfo.getFileName());
        fileVO.setStoreType(StoreEnum.DB.name());
        fileVO.setFileId(sysFileInfo.getId());
        return Result.ok(fileVO);
    }

    @Override
    public FileVO getFile(DownloadVO downloadVO) {
        SysFileInfo sysFileInfo = fileInfoService.getById(downloadVO.getFileId());
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        FileVO vo = new FileVO();
        vo.setFileName(sysFileInfo.getFileName());
        vo.setFileContent(sysFileInfo.getFileContent());
        return vo;
    }

    @Override
    public InputStream getFile(SysFileInfo sysFileInfo) throws Exception {
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        return new ByteArrayInputStream(sysFileInfo.getFileContent());
    }

    @Override
    public FileProperties getProperties() {
        return fileProperties;
    }

    @Override
    public String saveFileInfo(File file, String md5, String remark) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.DB.name());
        sysFileInfo.setFileName(file.getName());
        sysFileInfo.setFileContent(FileUtil.readBytes(file));
        sysFileInfo.setFileSize(FileUtil.size(file) / 1024);
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setRemark(remark);
        fileInfoService.save(sysFileInfo);
        return sysFileInfo.getId();
    }

    @Override
    public boolean delete(String fileId) throws Exception {
        final SysFileInfo sysFileInfo = fileInfoService.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return true;
        }
        return fileInfoService.removeById(fileId);
    }
}
