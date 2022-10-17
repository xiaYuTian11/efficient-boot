package com.efficient.task.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.task.api.TaskExecuteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/sysTask/execute/")
@Validated
@Api(tags = "定时任务启停相关")
@Permission
public class TaskExecuteController {

    @Autowired
    private TaskExecuteService taskExecuteService;

    /**
     * 启动定时任务
     */
    @GetMapping("/start")
    @ApiOperation(value = "启动定时任务", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "数据唯一标识", required = true)
    })
    public Result start(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.start(taskId);
    }

    /**
     * 停止定时任务
     */
    @GetMapping("/stop")
    @ApiOperation(value = "停止定时任务", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "数据唯一标识", required = true)
    })
    public Result stop(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.stop(taskId);
    }

    /**
     * 重启定时任务
     */
    @GetMapping("/restart")
    @ApiOperation(value = "重启定时任务", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "数据唯一标识", required = true)
    })
    public Result restart(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.restart(taskId);
    }

    /**
     * 删除定时任务
     */
    @GetMapping("/remove")
    @ApiOperation(value = "重启定时任务", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "数据唯一标识", required = true)
    })
    public Result remove(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.remove(taskId);
    }
}
