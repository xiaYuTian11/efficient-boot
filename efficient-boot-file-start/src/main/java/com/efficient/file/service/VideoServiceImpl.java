package com.efficient.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.Result;
import com.efficient.common.util.RedissonUtil;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.api.VideoService;
import com.efficient.file.constant.StoreEnum;
import com.efficient.file.model.dto.FileChunkDTO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.properties.FileProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

import static com.efficient.file.constant.FileConstant.*;

/**
 * @author TMW
 * @since 2024/1/29 11:13
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    private final String FILE_PATH = "%s" + File.separator + "%s" + File.separator + "%s";
    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Value("${com.efficient.cache.active:ehcache}")
    private String cacheType;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Result<SysFileInfo> chunkUpload(FileChunkDTO fileChunkDTO) throws Exception {
        String md5 = fileChunkDTO.getMd5();
        Integer totalChunk = fileChunkDTO.getTotalChunk();
        MultipartFile file = fileChunkDTO.getFile();
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();
        Integer currChunk = fileChunkDTO.getCurrChunk();
        Long chunkSize = fileChunkDTO.getChunkSize();
        String basePath = fileProperties.getLocal().getLocalPath() + UPLOAD_LINE + CHUNK_FILE + fileChunkDTO.getModule();
        if (fileProperties.getLocal().isAddDatePrefix()) {
            basePath += DateUtil.format(new Date(), "/yyyy/MM/dd/");
        }
        String destFilePath = String.format(FILE_PATH + ".%s", basePath, md5, md5, StringUtils.getFilenameExtension(filename));
        File destFile = new File(destFilePath);
        // 上传分片信息存放位置
        String confFile = String.format(FILE_PATH + ".conf", basePath, md5, md5);
        // 第一次创建分片记录文件
        // 创建目录
        File dir = new File(destFilePath).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
            // 所有分片状态设置为0
            byte[] bytes = new byte[totalChunk];
            Files.write(Paths.get(confFile), bytes);
        }
        // 随机分片写入文件
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(destFile, "rw");
             RandomAccessFile randomAccessConfFile = new RandomAccessFile(confFile, "rw");
             InputStream inputStream = file.getInputStream()) {
            // 定位到该分片的偏移量
            randomAccessFile.seek(currChunk * chunkSize);
            // 写入该分片数据
            randomAccessFile.write(IoUtil.readBytes(inputStream));
            // 定位到当前分片状态位置
            randomAccessConfFile.seek(currChunk);
            // 设置当前分片上传状态为1
            randomAccessConfFile.write(1);
        }
        SysFileInfo sysFileInfo = null;
        if (StrUtil.equals(cacheType, "redis")) {
            RedissonClient redissonClient = applicationContext.getBean(RedissonClient.class);
            String lockName = "chunkUpload:" + md5;
            sysFileInfo = RedissonUtil.execute(redissonClient, lockName, null, (param) -> {
                SysFileInfo sysFileInfoNew = sysFileInfoService.findByPathAndMd5(destFile.getAbsolutePath(), md5);
                if (Objects.isNull(sysFileInfoNew)) {
                    sysFileInfoNew = new SysFileInfo();
                    sysFileInfoNew.setStoreType(StoreEnum.LOCAL.name());
                    sysFileInfoNew.setFileName(file.getOriginalFilename());
                    sysFileInfoNew.setFilePath(destFile.getAbsolutePath());
                    sysFileInfoNew.setFileSize(totalChunk * chunkSize / KB);
                    sysFileInfoNew.setCreateTime(new Date());
                    sysFileInfoNew.setMd5(md5);
                    sysFileInfoNew.setIsIntact(CommonConstant.FALSE_INT);
                    sysFileInfoNew.setContentType(contentType);
                    sysFileInfoService.save(sysFileInfoNew);
                }
                return sysFileInfoNew;
            }, (param) -> sysFileInfoService.findByPathAndMd5(destFile.getAbsolutePath(), md5));
        } else {
            fileChunkDTO.setContentType(contentType);
            sysFileInfo = this.getFileInfo(fileChunkDTO, destFile.getAbsolutePath());
        }

        Result<String> stringResult = this.checkFile(fileChunkDTO.getModule(), fileChunkDTO.getMd5());
        String data = stringResult.getData();
        if (Objects.nonNull(sysFileInfo) && StrUtil.isNotBlank(data) && !data.contains("0")) {
            sysFileInfo.setIsIntact(CommonConstant.TRUE_INT);
            sysFileInfoService.updateById(sysFileInfo);
        }

        return Result.ok(sysFileInfo);
    }

    public synchronized SysFileInfo getFileInfo(FileChunkDTO fileChunkDTO, String destFile) {
        String md5 = fileChunkDTO.getMd5();
        Integer totalChunk = fileChunkDTO.getTotalChunk();
        Long chunkSize = fileChunkDTO.getChunkSize();
        SysFileInfo sysFileInfo = sysFileInfoService.findByPathAndMd5(destFile, md5);
        if (Objects.isNull(sysFileInfo)) {
            sysFileInfo = new SysFileInfo();
            sysFileInfo.setStoreType(StoreEnum.LOCAL.name());
            sysFileInfo.setFileName(fileChunkDTO.getFilename());
            sysFileInfo.setFilePath(destFile);
            sysFileInfo.setFileSize(totalChunk * chunkSize / KB);
            sysFileInfo.setCreateTime(new Date());
            sysFileInfo.setMd5(md5);
            sysFileInfo.setContentType(fileChunkDTO.getContentType());
            sysFileInfo.setIsIntact(CommonConstant.FALSE_INT);
            sysFileInfoService.save(sysFileInfo);
        }
        return sysFileInfo;
    }

    @Override
    public Result<String> checkFile(String module, String md5) throws Exception {
        String basePath = fileProperties.getLocal().getLocalPath() + UPLOAD_LINE + CHUNK_FILE + module;
        if (fileProperties.getLocal().isAddDatePrefix()) {
            basePath += DateUtil.format(new Date(), "/yyyy/MM/dd/");
        }
        String uploadPath = String.format(FILE_PATH + ".conf", basePath, md5, md5);
        Path path = Paths.get(uploadPath);
        // MD5目录不存在文件从未上传过
        if (!Files.exists(path.getParent())) {
            return Result.ok();
        }
        //判断文件是否上传成功
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = Files.readAllBytes(path);
        for (byte b : bytes) {
            stringBuilder.append(String.valueOf(b));
        }
        // 所有分片上传完成计算文件MD5
        if (!stringBuilder.toString().contains("0")) {
            File file = new File(String.format("%s" + File.separator + "%s", basePath, md5));
            if (!file.exists()) {
                return Result.ok(stringBuilder.toString().replaceAll("1", "0"));
            }
            File[] files = file.listFiles();
            if (Objects.isNull(files) || files.length < 2) {
                return Result.ok(stringBuilder.toString().replaceAll("1", "0"));
            }
            for (File f : files) {
                // 计算文件MD5是否相等
                if (!f.getName().contains("conf")) {
                    try (InputStream inputStream = Files.newInputStream(f.toPath())) {
                        String md5pwd = DigestUtils.md5DigestAsHex(inputStream);
                        if (!md5pwd.equalsIgnoreCase(md5)) {
                            return Result.ok(stringBuilder.toString().replaceAll("1", "0"));
                        }
                    }
                }
            }
        }

        // 文件未上传完成，反回每个分片状态，前端将未上传的分片继续上传
        return Result.ok(stringBuilder.toString());
    }

    @Override
    public Result<SysFileInfo> quickUpload(String module, String md5, String fileName, String remark) {
        String basePath = fileProperties.getLocal().getLocalPath() + UPLOAD_LINE + CHUNK_FILE + module;
        if (fileProperties.getLocal().isAddDatePrefix()) {
            basePath += DateUtil.format(new Date(), "/yyyy/MM/dd/");
        }
        String suffix = FileUtil.getSuffix(fileName);
        String uploadPath = String.format("%s\\%s\\%s.%s", basePath, md5, md5, suffix);
        File destFile = new File(uploadPath);
        String path = destFile.getAbsolutePath();
        SysFileInfo byPathAndMd5 = sysFileInfoService.findByPathAndMd5(path, md5);
        if (Objects.isNull(byPathAndMd5)) {
            byPathAndMd5 = new SysFileInfo();
            byPathAndMd5.setStoreType(StoreEnum.LOCAL.name());
            byPathAndMd5.setFileName(fileName);
            byPathAndMd5.setFilePath(destFile.getAbsolutePath());
            byPathAndMd5.setFileSize(destFile.length() / KB);
            byPathAndMd5.setCreateTime(new Date());
            byPathAndMd5.setIsIntact(1);
            byPathAndMd5.setRemark(remark);
            byPathAndMd5.setMd5(md5);
            byPathAndMd5.setContentType(FileUtil.getMimeType(destFile.getPath()));
        } else {
            byPathAndMd5.setId(null);
            byPathAndMd5.setBizId(null);
            byPathAndMd5.setRemark(remark);
            byPathAndMd5.setCreateTime(new Date());
            byPathAndMd5.setFileName(fileName);
        }

        sysFileInfoService.save(byPathAndMd5);
        return Result.ok(byPathAndMd5);
    }
}
