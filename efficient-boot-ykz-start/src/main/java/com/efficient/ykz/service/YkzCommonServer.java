package com.efficient.ykz.service;

import cn.hutool.crypto.SecureUtil;
import com.efficient.ykz.properties.YkzProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TMW
 * @since 2024/3/22 10:12
 */
@Service
public class YkzCommonServer {
    @Autowired
    private YkzProperties ykzProperties;

    public String encrypt(String password) {
        String saltValue = ykzProperties.getUserCenter().getSaltValue();
        boolean enableSalt = ykzProperties.getUserCenter().isEnableSalt();
        if (enableSalt) {
            return SecureUtil.md5(password + saltValue);
        }
        return SecureUtil.md5(password);
    }
}
