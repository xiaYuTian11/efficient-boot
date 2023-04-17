package com.efficient.file.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.result.Result;
import com.efficient.file.api.FileService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.properties.MinioProperties;
import com.efficient.file.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 10:51
 */
@Slf4j
public class MinioFileServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements FileService {

    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioUtil minioUtil;

    @Override
    public FileProperties getProperties() {
        return null;
    }

    @Override
    public Result upload(MultipartFile file, boolean unique) throws Exception {
        String fileName = minioUtil.upload(file, minioProperties.getBucketName());
        SysFileInfo sysFileInfo = this.saveFileInfo(file, fileName);
        FileVO fileVO = new FileVO();
        fileVO.setFileName(sysFileInfo.getFileName());
        fileVO.setFilePath(sysFileInfo.getFilePath());
        fileVO.setStoreType(StoreEnum.MINIO.name());
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
    public InputStream getFile(SysFileInfo sysFileInfo) throws Exception {
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        return minioUtil.getObject(minioProperties.getBucketName(), sysFileInfo.getFilePath());
    }

    @Override
    public String saveFileInfo(File file) {
        return null;
    }

    public SysFileInfo saveFileInfo(MultipartFile file, String fileName) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.MINIO.name());
        sysFileInfo.setFileName(file.getOriginalFilename());
        sysFileInfo.setFilePath(fileName);
        sysFileInfo.setRemark(minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName);

        sysFileInfo.setFileSize(file.getSize() / 1024);
        sysFileInfo.setCreateTime(new Date());
        this.save(sysFileInfo);
        return sysFileInfo;
    }

    @Override
    public boolean delete(String fileId) throws Exception {
        final SysFileInfo sysFileInfo = this.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return true;
        }
        minioUtil.removeObject(minioProperties.getBucketName(), sysFileInfo.getFilePath());

        return this.removeById(fileId);
    }
}
