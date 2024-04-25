package com.efficient.configs.config;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author TMW
 * @since 2024/4/25 10:47
 */
@Slf4j
@Component
public class FailedEvent implements ApplicationListener<ApplicationFailedEvent> {
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.error("application run failed , system exit right away!", event.getException());
        ThreadUtil.sleep(1000);
        System.exit(1);
    }
}
