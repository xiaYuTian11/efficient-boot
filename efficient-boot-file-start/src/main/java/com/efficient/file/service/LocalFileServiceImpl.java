package com.efficient.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.file.api.FileService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.dao.SysFileInfoMapper;
import com.efficient.file.dto.DownloadVO;
import com.efficient.file.entity.SysFileInfo;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.util.PathUtil;
import com.efficient.file.vo.FileVO;
import com.sjr.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import static com.efficient.file.constant.FileConstant.POINT;
import static com.efficient.file.constant.FileConstant.UPLOAD_LINE;

/**
 * 本地 文件操作 服务类
 *
 * @author TMW
 * @since 2022/4/26 9:29
 */
@Slf4j
public class LocalFileServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements FileService {
    @Autowired
    private FileProperties fileProperties;

    @Override
    public FileProperties getProperties() {
        return fileProperties;
    }

    @Override
    public Result upload(MultipartFile multipartFile, boolean unique) throws Exception {
        FileVO fileVo = new FileVO();

        // 根路径
        String basePath = fileProperties.getLocal().getLocalPath() + UPLOAD_LINE;
        // String basePath = "D:\\efficient\\file\\";
        // 获取文件的名称
        String originalFilename = multipartFile.getOriginalFilename();

        // 根据文件格式重新设置根路径
        if (originalFilename.lastIndexOf(POINT) != -1) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(POINT));
            basePath += PathUtil.getFileUrlFolder(suffix);
        }

        // 创建新的文件
        File fileExist = new File(basePath);
        // 文件夹不存在，则新建
        if (!fileExist.exists()) {
            fileExist.mkdirs();
        }

        // 重名文件重命名
        String fileName = originalFilename.replaceAll(" ", "");
        if (FileUtil.file(basePath, fileName).exists()) {
            if (unique) {
                FileUtil.del(new File(basePath + File.separator + fileName));
            } else {
                String name = fileName.substring(0, fileName.lastIndexOf(POINT)) + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
                fileName = name + Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(POINT));
            }
        }
        // 获取文件对象
        File realFile = new File(basePath, fileName);
        // 完成文件的上传
        // multipartFile.transferTo(realFile);
        FileUtil.writeBytes(multipartFile.getBytes(), realFile);
        fileVo.setFileName(realFile.getName());
        // fileVo.setFilePath(realFile.getAbsolutePath());
        fileVo.setStoreType(StoreEnum.LOCAL.name());
        // 保存文件信息
        String fileId = this.saveFileInfo(realFile);
        fileVo.setFileId(fileId);
        return Result.ok(fileVo);
    }

    @Override
    public FileVO getFile(DownloadVO downloadVO) {
        SysFileInfo sysFileInfo = this.getById(downloadVO.getFileId());
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        FileVO fileVO = new FileVO();
        File file = new File(sysFileInfo.getFilePath());
        if (!FileUtil.exist(file)) {
            return null;
        }
        fileVO.setFileName(file.getName());
        fileVO.setFileContent(FileUtil.readBytes(file));
        return fileVO;
    }

    @Override
    public String saveFileInfo(File file) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.LOCAL.name());
        sysFileInfo.setFileName(file.getName());
        sysFileInfo.setFilePath(file.getAbsolutePath());
        sysFileInfo.setFileSize(FileUtil.size(file) / 1024);
        sysFileInfo.setCreateTime(new Date());
        final boolean save = this.save(sysFileInfo);
        return sysFileInfo.getId();
    }

    @Override
    public boolean delete(String fileId) {
        final SysFileInfo sysFileInfo = this.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return true;
        }
        final String filePath = sysFileInfo.getFilePath();
        return this.removeById(fileId) && FileUtil.del(filePath);
    }
}
