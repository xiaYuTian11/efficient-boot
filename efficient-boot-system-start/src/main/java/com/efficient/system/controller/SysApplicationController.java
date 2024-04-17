package com.efficient.system.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.system.api.SysApplicationService;
import com.efficient.system.model.dto.SysApplicationDTO;
import com.efficient.system.model.dto.SysApplicationListDTO;
import com.efficient.system.model.entity.SysApplication;
import com.efficient.system.model.vo.SysApplicationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 系统第三方应用 controller 层
 * </p>
 *
 * @author TMW
 * @date 2024-04-16 16:02:49
 */
@RestController
@RequestMapping("/sysApplication")
@Validated
@Api(tags = "系统第三方应用")
@Permission
public class SysApplicationController {

    @Autowired
    private SysApplicationService sysApplicationService;

    /**
     * 新增
     */
    @Log(logOpt = LogEnum.SAVE, module = "系统第三方应用")
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public Result<SysApplication> save(@Validated @RequestBody SysApplicationDTO dto) {
        return sysApplicationService.save(dto);
    }

    /**
     * 详情
     */
    @Log(logOpt = LogEnum.QUERY, module = "系统第三方应用")
    @GetMapping("/findByAppCode")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appCode", value = "数据唯一标识", required = true)
    })
    public Result<SysApplicationVO> findByAppCode(@NotBlank(message = "appCode 不能为空") @RequestParam(name = "appCode") String appCode) {
        return sysApplicationService.findByAppCode(appCode);
    }

    /**
     * 修改
     */
    @Log(logOpt = LogEnum.UPDATE, module = "系统第三方应用")
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Boolean> update(@Validated @RequestBody SysApplicationDTO dto) {
        return sysApplicationService.update(dto);
    }

    /**
     * 删除
     */
    @Log(logOpt = LogEnum.DELETE, module = "系统第三方应用")
    @GetMapping("/delete")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<Boolean> delete(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        return sysApplicationService.delete(id);
    }

    /**
     * 列表
     */
    @Log(logOpt = LogEnum.PAGE, module = "系统第三方应用")
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = Result.class)
    public Result list(@Validated @RequestBody SysApplicationListDTO dto) {
        return Result.ok(sysApplicationService.list(dto));
    }

}
