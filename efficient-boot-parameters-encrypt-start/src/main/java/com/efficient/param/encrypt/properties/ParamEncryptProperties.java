package com.efficient.param.encrypt.properties;

import com.efficient.param.encrypt.constant.EnableType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2023/6/8 16:57
 */
@ConfigurationProperties("com.efficient.param")
@Data
public class ParamEncryptProperties {

    /**
     * 是否针对请求参数进行解密
     */
    private boolean requestEnable = true;
    private EnableType requestEnableType = EnableType.NEED;
    /**
     * 是否针对返回参数进行加密
     */
    private boolean responseEnable = true;
    
    private EnableType responseEnableType = EnableType.NEED;
    /**
     * AES KEY
     */
    private String encryptKey = "http://tanmw.top";
}
