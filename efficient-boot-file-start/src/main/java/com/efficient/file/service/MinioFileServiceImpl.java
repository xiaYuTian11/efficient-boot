package com.efficient.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.result.Result;
import com.efficient.file.api.FileService;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.properties.MinioProperties;
import com.efficient.file.util.FileMd5Util;
import com.efficient.file.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.efficient.file.constant.FileConstant.KB;

/**
 * @author TMW
 * @since 2022/8/26 10:51
 */
@Slf4j
public class MinioFileServiceImpl implements FileService {

    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private SysFileInfoService fileInfoService;
    @Autowired
    private FileProperties fileProperties;

    @Override
    public Result<FileVO> upload(MultipartFile file, boolean unique, String module, String md5, String remark) throws Exception {
        String fileName = minioUtil.upload(file, minioProperties.getBucketName());
        SysFileInfo sysFileInfo = this.saveFileInfo(file, fileName, remark, md5);
        FileVO fileVO = new FileVO();
        fileVO.setFileName(sysFileInfo.getFileName());
        fileVO.setFilePath(sysFileInfo.getFilePath());
        fileVO.setStoreType(StoreEnum.MINIO.name());
        fileVO.setFileId(sysFileInfo.getId());
        fileVO.setContentType(sysFileInfo.getContentType());
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
        return minioUtil.getObject(minioProperties.getBucketName(), sysFileInfo.getFilePath());
    }

    @Override
    public FileProperties getProperties() {
        return null;
    }

    @Override
    public SysFileInfo saveFileInfo(File file, String md5, String remark) {
        return null;
    }

    @Override
    public boolean delete(String fileId) throws Exception {
        final SysFileInfo sysFileInfo = fileInfoService.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return true;
        }
        minioUtil.removeObject(minioProperties.getBucketName(), sysFileInfo.getFilePath());

        return fileInfoService.removeById(fileId);
    }

    @Override
    public boolean deleteByBizId(String bizId) {
        List<SysFileInfo> sysFileInfos = fileInfoService.findByBizId(bizId);
        sysFileInfos.forEach(et -> {
            try {
                this.delete(et.getId());
            } catch (Exception e) {
                log.error("删除minio文件失败：", e);
            }
        });
        return true;
    }

    @Override
    public File getById(String fileId) {
        final SysFileInfo sysFileInfo = fileInfoService.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        try {
            InputStream inputStream = minioUtil.getObject(minioProperties.getBucketName(), sysFileInfo.getFilePath());
            File file = new File(fileProperties.getTempPath() + DateUtil.format(new Date(), "/yyyy/MM/dd/") + sysFileInfo.getFileName());
            FileUtil.writeFromStream(inputStream, file);
        } catch (Exception e) {
            log.error("获取文件失败", e);
            return null;
        }
        return null;
    }

    public SysFileInfo saveFileInfo(MultipartFile file, String fileName, String remark, String md5) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.MINIO.name());
        sysFileInfo.setFileName(file.getOriginalFilename());
        sysFileInfo.setFilePath(fileName);
        sysFileInfo.setRemark(minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName);
        sysFileInfo.setFileSize(file.getSize() / KB);
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setRemark(remark);
        sysFileInfo.setContentType(file.getContentType());
        if (StrUtil.isBlank(md5)) {
            sysFileInfo.setMd5(FileMd5Util.calculateMD5(file));
        }
        fileInfoService.save(sysFileInfo);
        return sysFileInfo;
    }
}
