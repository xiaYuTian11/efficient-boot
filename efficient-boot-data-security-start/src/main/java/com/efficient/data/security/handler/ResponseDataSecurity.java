package com.efficient.data.security.handler;

import cn.hutool.json.JSONUtil;
import com.efficient.common.exception.DataSecurityException;
import com.efficient.common.result.Result;
import com.efficient.data.security.annotation.ResponseEncrypt;
import com.efficient.data.security.annotation.SecuritySkip;
import com.efficient.data.security.constant.EnableType;
import com.efficient.data.security.properties.DataSecurityProperties;
import com.efficient.data.security.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author TMW
 * @since 2023/6/8 17:38
 */
@ConditionalOnProperty(name = "com.efficient.security.api.responseEnable", havingValue = "true")
@ControllerAdvice
@Slf4j
public class ResponseDataSecurity implements ResponseBodyAdvice<Result> {
    @Autowired
    private DataSecurityProperties properties;
    @Autowired
    private AESUtils aesUtils;

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        SecuritySkip skip = methodParameter.getMethodAnnotation(SecuritySkip.class);
        if (Objects.nonNull(skip) && skip.skipResponse()) {
            return false;
        }

        if (Objects.equals(properties.getApi().getResponseEnableType(), EnableType.NEED)) {
            return methodParameter.hasMethodAnnotation(ResponseEncrypt.class);
        } else if (Objects.equals(properties.getApi().getResponseEnableType(), EnableType.NODE)) {
            return true;
        }

        return false;
    }

    @Override
    public Result beforeBodyWrite(Result body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            Object bodyData = body.getData();
            if (bodyData != null) {
                body.setData(aesUtils.encrypt(JSONUtil.toJsonStr(bodyData)));
            }
        } catch (Exception e) {
            throw new DataSecurityException(e);
        }
        return body;
    }
}
