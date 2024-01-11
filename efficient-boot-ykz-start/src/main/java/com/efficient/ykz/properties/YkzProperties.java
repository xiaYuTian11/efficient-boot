package com.efficient.ykz.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@ConfigurationProperties("com.efficient.ykz")
@Data
public class YkzProperties {
    private YkzUserCenter userCenter = new YkzUserCenter();
    private YkzApi ykzApi = new YkzApi();

}
