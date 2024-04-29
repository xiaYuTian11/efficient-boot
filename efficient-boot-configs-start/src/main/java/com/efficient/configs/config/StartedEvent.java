package com.efficient.configs.config;

import com.efficient.common.api.StartedEventServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author TMW
 * @since 2024/2/5 9:23
 */
@Slf4j
@Component
public class StartedEvent implements ApplicationRunner {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取所有实现了 StartedEventServer 接口的 bean
        Map<String, StartedEventServer> startedEventServerBeans = applicationContext.getBeansOfType(StartedEventServer.class);
        log.info("Start StartedEventServer ...... ");
        // 打印所有实现类
        for (Map.Entry<String, StartedEventServer> entry : startedEventServerBeans.entrySet()) {
            StartedEventServer startedEventServer = entry.getValue();
            startedEventServer.init();
        }

        log.info("System startup successful !");
    }

}
