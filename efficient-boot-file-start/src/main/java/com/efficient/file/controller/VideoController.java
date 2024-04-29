package com.efficient.file.controller;

import cn.hutool.core.net.URLDecoder;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.api.VideoService;
import com.efficient.file.config.NonStaticResourceHttpRequestHandler;
import com.efficient.file.model.dto.FileChunkDTO;
import com.efficient.file.model.entity.SysFileInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author TMW
 * @since 2024/1/25 16:53
 */
@RestController
@RequestMapping("/video")
@Validated
@Api(tags = "视频操作/分片上传")
@Slf4j
@Permission
public class VideoController {

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private VideoService videoService;
    @Autowired
    private SysFileInfoService sysFileInfoService;

    /**
     * 文件分块上传
     * FORM-DATA 请求
     *
     * @param chunkSize  分块大小,字节
     * @param totalChunk 总块数量
     * @param currChunk  当前块数
     * @param md5        文件MD5值
     * @param module     所属模块
     * @param file       文件
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/chunkUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "分片上传", response = Result.class)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "chunkSize", value = "分块大小,字节", required = true),
    //         @ApiImplicitParam(name = "totalChunk", value = "总块数量", required = true),
    //         @ApiImplicitParam(name = "currChunk", value = "当前块数，从0开始", required = true),
    //         @ApiImplicitParam(name = "md5", value = "文件MD5值", required = true),
    //         @ApiImplicitParam(name = "file", value = "文件内容", required = true),
    //         @ApiImplicitParam(name = "module", value = "文件所属模块", defaultValue = "false"),
    //         @ApiImplicitParam(name = "remark", value = "备注", defaultValue = "false")
    // })
    public Result chunkUpload(@RequestParam Long chunkSize,
                              @RequestParam Integer totalChunk,
                              @RequestParam Integer currChunk,
                              @RequestParam String md5,
                              @RequestParam(value = "module", required = false) String module,
                              @RequestParam(value = "remark", required = false) String remark,
                              @RequestParam MultipartFile file) throws Exception {
        FileChunkDTO fileChunkDTO = new FileChunkDTO();
        fileChunkDTO.setMd5(md5);
        fileChunkDTO.setModule(module);
        fileChunkDTO.setFile(file);
        fileChunkDTO.setCurrChunk(currChunk);
        fileChunkDTO.setChunkSize(chunkSize);
        fileChunkDTO.setTotalSize(totalChunk * chunkSize);
        fileChunkDTO.setTotalChunk(totalChunk);
        fileChunkDTO.setRemark(remark);
        fileChunkDTO.setFilename(file.getOriginalFilename());
        return videoService.chunkUpload(fileChunkDTO);

    }

    /**
     * 秒传
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/quickUpload")
    @ApiOperation(value = "秒传", response = Result.class)
    public Result quickUpload(@RequestParam(value = "module", required = false) String module,
                              @RequestParam(value = "remark", required = false) String remark,
                              @RequestParam("md5") String md5,
                              @RequestParam("fileName") String fileName) throws Exception {
        return videoService.quickUpload(module, md5, fileName, remark);
    }

    /**
     * 获取文件分片状态，检测文件MD5合法性
     * FORM-DATA 请求
     *
     * @param md5
     * @return
     * @throws Exception
     */
    @PostMapping("/checkFile")
    @ApiOperation(value = "检查分片文件完整性", response = Result.class)
    public Result checkFile(@RequestParam(value = "module", required = false) String module,
                            @RequestParam("md5") String md5) throws Exception {
        return videoService.checkFile(module, md5);
    }

    @GetMapping(value = "/play")
    @ApiOperation(value = "播放视频文件", response = Result.class)
    public void play(@NotBlank(message = "fileId 不能为空") @RequestParam("fileId") String fileId) {
        SysFileInfo sysFileInfo = sysFileInfoService.getById(fileId);
        try {
            String path = sysFileInfo.getFilePath();
            File file = new File(path);
            if (file.exists()) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, path);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {
            log.error("文件访问失败", e);
        }
    }

    @GetMapping(value = "/playByPath")
    public void playByPath(@NotBlank(message = "filePath 不能为空") @RequestParam("filePath") String filePath) {
        try {
            String decode = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
            File file = new File(decode);
            if (file.exists()) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, decode);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {
            log.error("文件访问失败", e);
        }
    }

}
