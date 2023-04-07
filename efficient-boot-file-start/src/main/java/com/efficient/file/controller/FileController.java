package com.efficient.file.controller;

import cn.hutool.core.util.StrUtil;
import com.efficient.common.result.Result;
import com.efficient.common.util.JackSonUtil;
import com.efficient.file.api.FileService;
import com.efficient.file.constant.FileResultEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.entity.SysFileInfo;
import com.efficient.file.model.vo.FileVO;
import io.minio.GetObjectArgs;
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
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 9:29
 */
@RestController
@RequestMapping("/file")
@Validated
public class FileController {
    @Autowired
    private FileService fileService;
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
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "unique", required = false) boolean unique) throws Exception {
        if (file.isEmpty() || StrUtil.isBlank(file.getOriginalFilename())) {
            return Result.build(FileResultEnum.NOT_CHECK_FILE);
        }
        return fileService.upload(file, unique);
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        SysFileInfo sysFileInfo = fileService.getById(downloadVO.getFileId());
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
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    @PostMapping("/delete")
    public Result delete(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        boolean flag = fileService.delete(downloadVO.getFileId());
        return flag ? Result.ok() : Result.fail();
    }
}
