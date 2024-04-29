package com.efficient.system.service;

import com.efficient.common.api.StartedEventServer;
import com.efficient.system.api.DictCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author TMW
 * @since 2024/2/5 9:23
 */
@Slf4j
@Component
public class SystemStartedEvent implements StartedEventServer {
    @Autowired
    private DictCodeService dictCodeService;
    @Override
    public void init() {
        dictCodeService.init();
    }
}
