package com.efficient.file.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.model.dto.FileIdList;
import com.efficient.file.model.dto.FileInfoList;
import com.efficient.file.model.dto.FileStrList;
import com.efficient.file.model.entity.SysFileInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author TMW
 * @since 2024/3/4 13:53
 */
@RestController
@RequestMapping("/fileInfo")
@Validated
@Api(tags = "文件信息操作")
@Slf4j
@Permission
public class SysFileInfoController {

    @Autowired
    private SysFileInfoService fileInfoService;

    /**
     * 保存文件业务关联性-文件ID集合
     */
    @PostMapping("/saveIdListByBizId")
    @ApiOperation(value = "保存文件业务关联性-文件ID集合", response = Boolean.class)
    public Result<Boolean> saveIdListByBizId(@Validated @RequestBody FileIdList dto) {
        boolean saved = fileInfoService.saveIdListByBizId(dto.getFileIdList(), dto.getBizId());
        return saved ? Result.ok() : Result.fail();
    }

    /**
     * 保存文件业务关联性-文件ID集合
     */
    @PostMapping("/saveFileListByBizId")
    @ApiOperation(value = "保存文件业务关联性-文件对象集合", response = Boolean.class)
    public Result<Boolean> saveFileListByBizId(@Validated @RequestBody FileInfoList dto) {
        boolean saved = fileInfoService.saveFileListByBizId(dto.getFileList(), dto.getBizId());
        return saved ? Result.ok() : Result.fail();
    }

    /**
     * 保存文件业务关联性-文件ID集合
     */
    @PostMapping("/saveStrListByBizId")
    @ApiOperation(value = "保存文件业务关联性-文件ID字符串", response = Boolean.class)
    public Result<Boolean> saveStrListByBizId(@Validated @RequestBody FileStrList dto) {
        boolean saved = fileInfoService.saveStrListByBizId(dto.getFileIdStr(), dto.getBizId());
        return saved ? Result.ok() : Result.fail();
    }

    /**
     * 根据业务主键获取关联的文件信息
     */
    @GetMapping("/findByBizId")
    @ApiOperation(value = "根据业务主键获取关联的文件信息", response = SysFileInfo.class)
    public Result<List<SysFileInfo>> findByBizId(@NotBlank(message = "bizId 不能为空") String bizId) {
        return Result.ok(fileInfoService.findByBizId(bizId));
    }

    /**
     * 根据主键获取文件信息
     */
    @GetMapping("/findById")
    @ApiOperation(value = "根据主键获取文件信息", response = SysFileInfo.class)
    public Result<SysFileInfo> findById(@NotBlank(message = "id 不能为空") String id) {
        return Result.ok(fileInfoService.findById(id));
    }

    /**
     * 根据路径获取文件信息
     */
    @GetMapping("/findByPath")
    @ApiOperation(value = "根据路径获取文件信息", response = SysFileInfo.class)
    public Result<SysFileInfo> findByPath(@NotBlank(message = "filePath 不能为空") String filePath) {
        return Result.ok(fileInfoService.findByPath(filePath));
    }

}
