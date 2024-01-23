package com.efficient.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/10/28 11:45
 */
@ConfigurationProperties("com.efficient.auth")
@Data
public class AuthProperties {
    private LoginProperties login = new LoginProperties();
}
