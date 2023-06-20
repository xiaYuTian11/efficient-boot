package com.efficient.param.encrypt.handler;

import com.efficient.param.encrypt.annotation.ParamDecrypt;
import com.efficient.param.encrypt.annotation.ParamSkip;
import com.efficient.param.encrypt.constant.EnableType;
import com.efficient.param.encrypt.properties.ParamEncryptProperties;
import com.efficient.param.encrypt.util.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author TMW
 * @since 2023/6/8 17:38
 */
@ConditionalOnProperty(name = "com.efficient.param.requestEnable", havingValue = "true")
@ControllerAdvice
public class ParamDecryptRequest extends RequestBodyAdviceAdapter {
    @Autowired
    private ParamEncryptProperties properties;
    @Autowired
    private AESUtils aesUtils;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        ParamSkip paramSkip = methodParameter.getMethodAnnotation(ParamSkip.class);
        if (Objects.nonNull(paramSkip) && paramSkip.skipDecrypt()) {
            return false;
        }

        if (Objects.equals(properties.getRequestEnableType(), EnableType.NEED)) {
            return methodParameter.hasMethodAnnotation(ParamDecrypt.class);
        }

        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] body = new byte[inputMessage.getBody().available()];
        inputMessage.getBody().read(body);
        try {
            byte[] decrypt = aesUtils.decrypt(body);
            ByteArrayInputStream data = new ByteArrayInputStream(decrypt);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return data;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }
}
