package com.efficient.logs.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.constant.LogEnum;
import com.efficient.logs.model.dto.SysLogListDTO;
import com.efficient.logs.model.vo.SysLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 日志管理
 *
 * @author TMW
 * @since 2024/4/19 10:33
 */
@RestController
@RequestMapping("/sysLog")
@Validated
@Api(tags = "日志管理")
@Permission
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @Log(logOpt = LogEnum.PAGE, module = "日志管理")
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = SysLogVO.class)
    public Result list(@Validated @RequestBody SysLogListDTO dto) {
        return Result.ok(sysLogService.list(dto));
    }

    /**
     * 查找
     */
    @Log(logOpt = LogEnum.QUERY, module = "日志管理")
    @PostMapping("/find")
    @ApiOperation(value = "查找", response = SysLogVO.class)
    public Result find(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        return Result.ok(sysLogService.find(id));
    }
}
