package com.efficient.task.config;

import cn.hutool.core.collection.CollUtil;
import com.efficient.task.api.SysTaskService;
import com.efficient.task.constant.TaskStatusEnum;
import com.efficient.task.model.entity.SysTask;
import com.efficient.task.properties.TaskProperties;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MapperConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.efficient.task.constant.TaskConstant.JOB_GROUP;
import static com.efficient.task.constant.TaskConstant.TRIGGER_GROUP;

/**
 * @author TMW
 * @since 2022/8/28 18:10
 */
@Configuration
@EnableConfigurationProperties(TaskProperties.class)
@MapperScan(basePackages = {"com.efficient.task.dao"})
// @ConditionalOnProperty(name = "com.efficient.task.enable", havingValue = "true", matchIfMissing = true)
@Slf4j
public class TaskConfig {
    @Autowired
    private TaskProperties taskProperties;
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void initTask() {
        if (!taskProperties.getEnable()) {
            return;
        }
        try {
            List<SysTask> taskList = sysTaskService.findAll();
            if (CollUtil.isEmpty(taskList)) {
                log.info(" No scheduled tasks!");
                return;
            }
            for (SysTask sysTask : taskList) {
                String taskClass = sysTask.getTaskClass();
                Class<Job> aClass = null;
                try {
                    Class<?> bClass = Class.forName(taskClass);
                    if (!QuartzJobBean.class.isAssignableFrom(bClass) && !Job.class.isAssignableFrom(bClass)) {
                        log.error("配置的类名需实现QuartzJobBean或者Job接口，请检查配置表！");
                        continue;
                    }
                    aClass = (Class<Job>) bClass;
                } catch (ClassNotFoundException e) {
                    log.error("配置的类名错误，请检查配置表！", e);
                    continue;
                }

                JobDetail taskTest = JobBuilder.newJob(aClass)
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
                log.info("成功加载定时任务：" + sysTask.getTaskCode());
                sysTask.setTaskStatus(TaskStatusEnum.START.getCode());
            }
            sysTaskService.saveOrUpdateBatch(taskList);
        } catch (Exception e) {
            log.error("定时任务启动失败！", e);
        }
    }
}
