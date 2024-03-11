package com.efficient.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.common.validate.Common1Group;
import com.efficient.common.validate.Common2Group;
import com.efficient.file.api.FileService;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.constant.FileResultEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.util.FileMd5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 9:29
 */
@RestController
@RequestMapping("/file")
@Validated
@Api(tags = "文件操作")
@Slf4j
@Permission
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private HttpServletResponse response;

    /**
     * 上传文件
     *
     * @param file   文件
     * @param unique 是否唯一
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件标识", required = true),
            @ApiImplicitParam(name = "unique", value = "是否唯一文件，true标识删除现有同名文件", defaultValue = "false"),
            @ApiImplicitParam(name = "module", value = "文件所属模块", defaultValue = "false")
    })
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "unique", required = false) boolean unique,
                         @RequestParam(value = "module", required = false) String module,
                         @RequestParam(value = "remark", required = false) String remark) throws Exception {
        if (file.isEmpty() || StrUtil.isBlank(file.getOriginalFilename())) {
            return Result.build(FileResultEnum.NOT_CHECK_FILE);
        }

        String md5 = FileMd5Util.getMD5ByApacheCommonsCodec(file);
        return fileService.upload(file, unique, module, md5, remark);
    }

    @PostMapping("/download")
    @ApiOperation(value = "根据Id下载")
    public ResponseEntity<byte[]> download(@Validated(value = Common1Group.class)
                                           @RequestBody DownloadVO downloadVO) throws Exception {
        SysFileInfo sysFileInfo = sysFileInfoService.getById(downloadVO.getFileId());
        ResponseEntity<byte[]> responseEntity = null;
        if (Objects.isNull(sysFileInfo)) {
            return responseEntity;
        }

        ByteArrayOutputStream out = null;
        try (InputStream in = fileService.getFile(sysFileInfo)) {
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            // 封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(sysFileInfo.getFileName(), StandardCharsets.UTF_8.name()));
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Collections.singletonList("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("文件下载异常：", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("文件下载-关闭文件流异常：", e);
            }
        }
        return responseEntity;
    }

    @PostMapping("/downloadByPath")
    @ApiOperation(value = "根据路径下载")
    public ResponseEntity<byte[]> downloadByPath(@Validated(value = Common2Group.class)
                                                 @RequestBody DownloadVO downloadVO) throws Exception {
        String filePath = downloadVO.getFilePath();
        ResponseEntity<byte[]> responseEntity = null;
        if (StrUtil.isBlank(filePath)) {
            return responseEntity;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return responseEntity;
        }

        ByteArrayOutputStream out = null;
        try (InputStream in = Files.newInputStream(file.toPath())) {
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            // 封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(FileUtil.getName(filePath), StandardCharsets.UTF_8.name()));
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Collections.singletonList("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("文件下载异常：", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("文件下载-关闭文件流异常：", e);
            }
        }
        return responseEntity;
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据文件id删除文件")
    public Result delete(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        boolean flag = fileService.delete(downloadVO.getFileId());
        return flag ? Result.ok() : Result.fail();
    }

    @PostMapping("/deleteByBizId")
    @ApiOperation(value = "根据业务ID删除文件")
    public Result deleteByBizId(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        boolean flag = fileService.deleteByBizId(downloadVO.getBizId());
        return flag ? Result.ok() : Result.fail();
    }
}
