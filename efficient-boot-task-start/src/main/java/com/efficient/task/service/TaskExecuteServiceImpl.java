package com.efficient.task.service;

import com.efficient.task.api.SysTaskService;
import com.efficient.task.api.TaskExecuteService;
import com.efficient.task.constant.TaskResultEnum;
import com.efficient.task.constant.TaskStatusEnum;
import com.efficient.task.model.entity.SysTask;
import com.sjr.common.result.Result;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.efficient.task.constant.TaskConstant.JOB_GROUP;
import static com.efficient.task.constant.TaskConstant.TRIGGER_GROUP;

/**
 * @author TMW
 * @since 2022/8/28 21:56
 */
@Service
public class TaskExecuteServiceImpl implements TaskExecuteService {
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private Scheduler scheduler;

    @Override
    public Result start(String taskId) throws Exception {
        SysTask sysTask = sysTaskService.getById(taskId);
        if (Objects.isNull(sysTask)) {
            return Result.build(TaskResultEnum.NOT_CHECK_FILE);
        }
        JobKey jobKey = new JobKey(sysTask.getTaskCode(), JOB_GROUP);
        // 如果存在这个任务，则删除
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        JobDetail taskTest = JobBuilder.newJob((Class<Job>) Class.forName(sysTask.getTaskClass()))
                .withIdentity(sysTask.getTaskCode(), JOB_GROUP)
                // 即使没有Trigger关联时，也不需要删除该JobDetail
                .storeDurably()
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysTask.getCronExpression());
        final CronTrigger build = TriggerBuilder.newTrigger()
                .forJob(taskTest)
                .withIdentity(sysTask.getTaskCode(), TRIGGER_GROUP)
                .withSchedule(cronScheduleBuilder)
                .build();
        scheduler.scheduleJob(taskTest, build);
        sysTask.setTaskStatus(TaskStatusEnum.START.getCode());
        sysTaskService.updateById(sysTask);
        return Result.ok();
    }

    @Override
    public Result stop(String taskId) throws Exception {
        SysTask sysTask = sysTaskService.getById(taskId);
        if (Objects.isNull(sysTask)) {
            return Result.build(TaskResultEnum.NOT_CHECK_FILE);
        }
        JobKey jobKey = JobKey.jobKey(sysTask.getTaskCode(), JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return Result.ok();
        }
        scheduler.pauseJob(jobKey);
        sysTask.setTaskStatus(TaskStatusEnum.STOP.getCode());
        sysTaskService.updateById(sysTask);
        return Result.ok();
    }

    @Override
    public Result restart(String taskId) throws Exception {
        SysTask sysTask = sysTaskService.getById(taskId);
        if (Objects.isNull(sysTask)) {
            return Result.build(TaskResultEnum.NOT_CHECK_FILE);
        }
        JobKey jobKey = JobKey.jobKey(sysTask.getTaskCode(), JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return Result.ok();
        }
        scheduler.resumeJob(jobKey);
        sysTask.setTaskStatus(TaskStatusEnum.START.getCode());
        sysTaskService.updateById(sysTask);
        return Result.ok();
    }

    @Override
    public Result remove(String taskId) throws Exception {
        SysTask sysTask = sysTaskService.getById(taskId);
        if (Objects.isNull(sysTask)) {
            return Result.build(TaskResultEnum.NOT_CHECK_FILE);
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(sysTask.getTaskCode(), TRIGGER_GROUP);
        JobKey jobKey = JobKey.jobKey(sysTask.getTaskCode(), JOB_GROUP);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return Result.ok();
        }
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
        sysTask.setTaskStatus(TaskStatusEnum.REMOVE.getCode());
        sysTaskService.updateById(sysTask);
        return Result.ok();
    }
}
