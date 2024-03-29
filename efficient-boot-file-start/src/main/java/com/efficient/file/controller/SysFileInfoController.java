package com.efficient.file.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.file.api.SysFileInfoService;
import com.efficient.file.model.dto.FileBizRelation;
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
     * 保存文件业务关联性
     */
    @PostMapping("/saveListByBizId")
    @ApiOperation(value = "保存文件业务关联性", response = Boolean.class)
    public Result<Boolean> saveListByBizId(@Validated @RequestBody FileBizRelation dto) {
        boolean saved = fileInfoService.saveListByBizId(dto.getFileIdList(), dto.getBizId());
        return saved ? Result.ok() : Result.fail();
    }

    /**
     * 根据业务主键获取关联的文件信息
     */
    @GetMapping("/findByBizId")
    @ApiOperation(value = "根据业务主键获取关联的文件信息", response = Boolean.class)
    public Result<List<SysFileInfo>> findByBizId(@NotBlank(message = "bizId 不能为空") String bizId) {
        return Result.ok(fileInfoService.findByBizId(bizId));
    }

}
