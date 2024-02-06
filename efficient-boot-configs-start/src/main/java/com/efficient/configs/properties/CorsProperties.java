package com.efficient.configs.properties;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/2/5 9:29
 */
@Data
public class CorsProperties {
    /**
     * 是否启用全局跨域
     */
    private boolean enable = true;
}
