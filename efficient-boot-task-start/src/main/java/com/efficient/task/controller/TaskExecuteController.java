package com.efficient.task.controller;

import com.efficient.common.result.Result;
import com.efficient.task.api.TaskExecuteService;
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
public class TaskExecuteController {

    @Autowired
    private TaskExecuteService taskExecuteService;

    /**
     * 启动定时任务
     */
    @GetMapping("/start")
    public Result start(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.start(taskId);
    }

    /**
     * 停止定时任务
     */
    @GetMapping("/stop")
    public Result stop(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.stop(taskId);
    }

    /**
     * 重启定时任务
     */
    @GetMapping("/restart")
    public Result restart(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.restart(taskId);
    }

    /**
     * 删除定时任务
     */
    @GetMapping("/remove")
    public Result remove(@NotBlank(message = "taskId 不能为空") @RequestParam(name = "taskId") String taskId) throws Exception {
        return taskExecuteService.remove(taskId);
    }
}
