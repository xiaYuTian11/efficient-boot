package com.efficient.logs.event;

import com.efficient.logs.model.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 自定义需要发布的事件类，需要继承ApplicationEvent类或PayloadApplicationEvent(该类也仅仅是对ApplicationEvent的一层封装)
 * @author TMW
 * @since 2023/5/8 17:03
 */
@Slf4j
@Component
public class LogEventListener {
    @EventListener(condition = "#sysLog.id != null ")
    public void handleEvent(SysLog sysLog) {
        log.info("通过 event 保存 日志");
    }
}
