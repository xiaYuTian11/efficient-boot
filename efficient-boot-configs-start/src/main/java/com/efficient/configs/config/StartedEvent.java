package com.efficient.configs.config;

import com.efficient.configs.api.StartedEventServer;
import com.efficient.system.api.DictCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author TMW
 * @since 2024/2/5 9:23
 */
@Slf4j
@Component
public class StartedEvent implements ApplicationRunner {
    @Autowired
    private DictCodeService dictCodeService;
    @Autowired(required = false)
    private StartedEventServer startedEventServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Objects.nonNull(startedEventServer)) {
            log.info("Start StartedEventServer ...... ");
            startedEventServer.init();
        }

        dictCodeService.init();
        log.info("System startup successful !");
    }
}
