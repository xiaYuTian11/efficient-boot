package com.efficient.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.result.Result;
import com.efficient.file.api.FileService;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

import static com.efficient.file.constant.FileConstant.*;

/**
 * 本地 文件操作 服务类
 *
 * @author TMW
 * @since 2022/4/26 9:29
 */
@Slf4j
public class LocalFileServiceImpl implements FileService {
    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private SysFileInfoService fileInfoService;

    @Override
    public Result<FileVO> upload(MultipartFile multipartFile, boolean unique, String module, String md5, String remark) throws Exception {
        FileVO fileVo = new FileVO();
        if (StrUtil.isBlank(module)) {
            module = "";
        }
        // 根路径
        String basePath = fileProperties.getLocal().getLocalPath() + UPLOAD_LINE + module;
        // String basePath = "D:\\efficient\\file\\";
        // 获取文件的名称
        String originalFilename = multipartFile.getOriginalFilename();

        // 根据文件格式重新设置根路径
        if (originalFilename.lastIndexOf(POINT) != -1) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(POINT));
            basePath += PathUtil.getFileUrlFolder(suffix);
        }
        if (fileProperties.getLocal().isAddDatePrefix()) {
            basePath += DateUtil.format(new Date(), "/yyyy/MM/dd/");
        }

        // 创建新的文件
        File fileExist = new File(basePath);
        // 文件夹不存在，则新建
        if (!fileExist.exists()) {
            fileExist.mkdirs();
        }

        // 重名文件重命名
        String fileName = originalFilename.replaceAll(" ", "");
        if (unique) {
            FileUtil.del(new File(basePath + File.separator + fileName));
        }

        int pointIndexOf = fileName.lastIndexOf(POINT);
        String suffix = fileName.substring(pointIndexOf);
        int fileNum = 1;
        String nameDefault = fileName.substring(0, fileName.lastIndexOf(POINT)) + HORIZONTAL_BAR + 1 + suffix;
        String name = fileName;
        while (FileUtil.file(basePath, name).exists()) {
            int lastIndexOf = name.lastIndexOf(HORIZONTAL_BAR);
            if (lastIndexOf > 0) {
                String subStr = name.substring(lastIndexOf + 1, name.lastIndexOf(POINT));
                if (NumberUtil.isNumber(subStr)) {
                    fileNum = Integer.parseInt(subStr) + 1;
                    name = name.substring(0, lastIndexOf) + HORIZONTAL_BAR + fileNum + suffix;
                } else {
                    name = nameDefault;
                }
            } else {
                name = nameDefault;
            }
        }
        fileName = name;
        // 获取文件对象
        File realFile = new File(basePath, fileName);
        // 完成文件的上传
        // multipartFile.transferTo(realFile);
        FileUtil.writeBytes(multipartFile.getBytes(), realFile);
        fileVo.setFileName(realFile.getName());
        // fileVo.setFilePath(realFile.getAbsolutePath());
        fileVo.setStoreType(StoreEnum.LOCAL.name());
        // 保存文件信息
        String fileId = this.saveFileInfo(realFile, md5, remark);
        fileVo.setFileId(fileId);
        return Result.ok(fileVo);
    }

    @Override
    public FileVO getFile(DownloadVO downloadVO) {
        SysFileInfo sysFileInfo = fileInfoService.getById(downloadVO.getFileId());
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
    public InputStream getFile(SysFileInfo sysFileInfo) throws Exception {
        if (Objects.isNull(sysFileInfo)) {
            return null;
        }
        File file = new File(sysFileInfo.getFilePath());
        if (!FileUtil.exist(file)) {
            return null;
        }
        return new ByteArrayInputStream(FileUtil.readBytes(file));
    }

    @Override
    public FileProperties getProperties() {
        return fileProperties;
    }

    @Override
    public boolean delete(String fileId) throws Exception {
        final SysFileInfo sysFileInfo = fileInfoService.getById(fileId);
        if (Objects.isNull(sysFileInfo)) {
            return true;
        }
        final String filePath = sysFileInfo.getFilePath();
        return fileInfoService.removeById(fileId) && FileUtil.del(filePath);
    }

    @Override
    public String saveFileInfo(File file, String md5, String remark) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setStoreType(StoreEnum.LOCAL.name());
        sysFileInfo.setFileName(file.getName());
        sysFileInfo.setFilePath(file.getAbsolutePath());
        sysFileInfo.setFileSize(FileUtil.size(file) / KB);
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setMd5(md5);
        sysFileInfo.setRemark(remark);
        final boolean save = fileInfoService.save(sysFileInfo);
        return sysFileInfo.getId();
    }
}
