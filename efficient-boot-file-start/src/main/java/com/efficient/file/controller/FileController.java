package com.efficient.file.controller;

import cn.hutool.core.util.StrUtil;
import com.efficient.common.result.Result;
import com.efficient.common.util.JackSonUtil;
import com.efficient.file.api.FileService;
import com.efficient.file.constant.FileResultEnum;
import com.efficient.file.model.dto.DownloadVO;
import com.efficient.file.model.vo.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/8/26 9:29
 */
@RestController
@RequestMapping("/file")
@Validated
@Api(tags = "文件操作")
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
    @ApiOperation(value = "上传", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件标识", required = true),
            @ApiImplicitParam(name = "unique", value = "是否唯一文件，true标识删除现有同名文件", defaultValue = "false")
    })
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "unique", required = false) boolean unique) throws Exception {
        if (file.isEmpty() || StrUtil.isBlank(file.getOriginalFilename())) {
            return Result.build(FileResultEnum.NOT_CHECK_FILE);
        }
        return fileService.upload(file, unique);
    }

    @PostMapping("/download")
    @ApiOperation(value = "下载")
    public void download(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        FileVO file = fileService.getFile(downloadVO);
        if (Objects.isNull(file)) {
            response.reset();
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JackSonUtil.toJson(Result.build(FileResultEnum.FILE_NOT_EXISTS)));
            response.flushBuffer();
            return;
        }
        final byte[] bytes = file.getFileContent();
        String fileName = URLEncoder.encode(file.getFileName(), "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "下载")
    public Result delete(@Validated @RequestBody DownloadVO downloadVO) throws Exception {
        boolean flag = fileService.delete(downloadVO.getFileId());
        return flag ? Result.ok() : Result.fail();
    }
}
