package com.efficient.task.controller;

import com.efficient.task.api.SysTaskService;
import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.dto.SysTaskListDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
import com.efficient.common.log.Log;
import com.efficient.common.log.OptTypeEnum;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
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
 * 定时任务表 controller 层
 * </p>
 *
 * @author code generator
 * @date 2022-08-28 18:08:05
 */
@RestController
@RequestMapping("/sysTask")
@Validated
@Api(tags = "定时任务表")
@Permission
public class SysTaskController {

    @Autowired
    private SysTaskService sysTaskService;

    /**
     * 新增
     */
    @Log(optType = OptTypeEnum.INSERT)
    @PostMapping("/save")
    @ApiOperation(value = "保存", response = Result.class)
    public Result save(@Validated @RequestBody SysTaskDTO dto) {
        SysTask entity = sysTaskService.save(dto);
        return Result.ok(entity);
    }

    /**
     * 详情
     */
    @Log(optType = OptTypeEnum.QUERY)
    @GetMapping("/find")
    @ApiOperation(value = "详情", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result find(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        SysTaskVO entity = sysTaskService.findById(id);
        return Result.ok(entity);
    }

    /**
     * 修改
     */
    @Log(optType = OptTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = Result.class)
    public Result update(@Validated @RequestBody SysTaskDTO dto) {
        boolean flag = sysTaskService.update(dto);
        return flag ? Result.ok() : Result.fail();
    }

    /**
     * 删除
     */
    @Log(optType = OptTypeEnum.DELETE)
    @GetMapping("/delete")
    @ApiOperation(value = "删除", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result delete(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        boolean flag = sysTaskService.delete(id);
        return flag ? Result.ok() : Result.fail();
    }

    /**
     * 列表
     */
    @Log(optType = OptTypeEnum.PAGE)
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = Result.class)
    public Result list(@Validated @RequestBody SysTaskListDTO dto) {
        return Result.ok(sysTaskService.list(dto));
    }
}
