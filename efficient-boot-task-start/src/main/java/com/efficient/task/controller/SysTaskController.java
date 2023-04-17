package com.efficient.task.controller;

import com.efficient.common.result.Result;
import com.efficient.task.api.SysTaskService;
import com.efficient.task.model.dto.SysTaskDTO;
import com.efficient.task.model.dto.SysTaskListDTO;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.model.vo.SysTaskVO;
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
public class SysTaskController {

    @Autowired
    private SysTaskService sysTaskService;

    /**
     * 新增
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody SysTaskDTO dto) {
        SysTask entity = sysTaskService.save(dto);
        return Result.ok(entity);
    }

    /**
     * 详情
     */
    @GetMapping("/find")
    public Result find(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        SysTaskVO entity = sysTaskService.findById(id);
        return Result.ok(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result update(@Validated @RequestBody SysTaskDTO dto) {
        boolean flag = sysTaskService.update(dto);
        return flag ? Result.ok() : Result.fail();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public Result delete(@NotBlank(message = "id 不能为空") @RequestParam(name = "id") String id) {
        boolean flag = sysTaskService.delete(id);
        return flag ? Result.ok() : Result.fail();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(@Validated @RequestBody SysTaskListDTO dto) {
        return Result.ok(sysTaskService.list(dto));
    }
}
