package com.efficient.form.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.form.api.DynamicFormsService;
import com.efficient.form.model.dto.DynamicFormsDTO;
import com.efficient.form.model.dto.DynamicFormsListDTO;
import com.efficient.form.model.entity.DynamicForms;
import com.efficient.form.model.vo.DynamicFormsVO;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统动态表单 controller 层
 * </p>
 *
 * @author TMW
 * @date 2024-07-12 15:09:51
 */
@RestController
@RequestMapping("/dynamicForms")
@Validated
@Api(tags = "系统动态表单")
@Permission
public class DynamicFormsController {

    @Autowired
    private DynamicFormsService dynamicFormsService;

    /**
     * 新增
     */
    @Log(logOpt = LogEnum.SAVE, module = "系统动态表单")
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public Result<DynamicForms> save(@Validated @RequestBody DynamicFormsDTO dto) {
        return dynamicFormsService.save(dto);
    }

    /**
     * 详情
     */
    @Log(logOpt = LogEnum.QUERY, module = "系统动态表单")
    @GetMapping("/find")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<DynamicFormsVO> find(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        return dynamicFormsService.findById(id);
    }

    /**
     * 修改
     */
    @Log(logOpt = LogEnum.UPDATE, module = "系统动态表单")
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Boolean> update(@Validated @RequestBody DynamicFormsDTO dto) {
        return dynamicFormsService.update(dto);
    }

    /**
     * 删除
     */
    @Log(logOpt = LogEnum.DELETE, module = "系统动态表单")
    @GetMapping("/delete")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<Boolean> delete(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        return dynamicFormsService.delete(id);
    }

    /**
     * 改变是否启用
     */
    @Log(logOpt = LogEnum.DELETE, module = "系统动态表单")
    @GetMapping("/changeEnabled")
    @ApiOperation(value = "改变是否启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true),
            @ApiImplicitParam(name = "isEnabled", value = "是否启用，1-是，0-否", required = true),
    })
    public Result<Boolean> changeEnabled(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id,
                                         @NotNull(message = "isEnabled 不能为空") @RequestParam(name = "isEnabled") Integer isEnabled) {
        return dynamicFormsService.changeEnabled(id, isEnabled);
    }

    /**
     * 列表
     */
    @Log(logOpt = LogEnum.PAGE, module = "系统动态表单")
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = Result.class)
    public Result list(@Validated @RequestBody DynamicFormsListDTO dto) {
        return Result.ok(dynamicFormsService.list(dto));
    }

}
