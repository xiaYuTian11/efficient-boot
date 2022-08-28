package com.efficient.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@ConfigurationProperties("com.efficient.task")
@Data
public class TaskProperties {
    private Boolean enable = true;

}
