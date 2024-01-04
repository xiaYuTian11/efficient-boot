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
    private String ip = "https://uc-openplatform.bigdatacq.com:4403";
    private String appId;
    private String appSecret;
    private YkzUserCenter userCenter;

}
