package com.efficient.data.security.properties;

import com.efficient.data.security.constant.EnableType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2023/6/8 16:57
 */
@ConfigurationProperties("com.efficient.data")
@Data
public class DataSecurityProperties {

    /**
     * 是否针对请求参数进行解密
     */
    private boolean requestEnable = true;
    /**
     * 请求值加密类型
     */
    private EnableType requestEnableType = EnableType.NEED;
    /**
     * 是否针对返回参数进行加密
     */
    private boolean responseEnable = true;
    /**
     * 返回值加密类型
     */
    private EnableType responseEnableType = EnableType.NEED;
    /**
     * AES KEY
     */
    private String encryptKey = "http://tanmw.top";
    /**
     * 是否启用数据库加密
     */
    private boolean dbEncryptEnable = true;
    /**
     * db AES KEY
     */
    private String dbEncryptKey = "http://tanmw.top";
    /**
     * 加解密的实体类全限定名路径，多个用逗号隔开，比如com.zenith.front.model
     */
    private String dbEncryptModelPath = "top.tanmw.demo.model";
}
