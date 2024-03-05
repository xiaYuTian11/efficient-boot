package com.efficient.task.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.task.api.SysTaskService;
import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.dto.SysTaskListDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
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
@Api(tags = "定时任务信息")
@Permission
public class SysTaskController {

    @Autowired
    private SysTaskService sysTaskService;

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存", response = SysTask.class)
    public Result<SysTask> save(@Validated @RequestBody SysTaskDTO dto) {
        return sysTaskService.save(dto);
    }

    /**
     * 详情
     */
    @GetMapping("/find")
    @ApiOperation(value = "详情", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<SysTaskVO> find(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        SysTaskVO entity = sysTaskService.findById(id);
        return Result.ok(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = String.class)
    public Result<Boolean> update(@Validated @RequestBody SysTaskDTO dto) {
        return sysTaskService.update(dto);
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<String> delete(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        boolean flag = sysTaskService.delete(id);
        return flag ? Result.ok() : Result.fail();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = SysTask.class)
    public Result<Page<SysTask>> list(@Validated @RequestBody SysTaskListDTO dto) {
        return Result.ok(sysTaskService.list(dto));
    }
}
