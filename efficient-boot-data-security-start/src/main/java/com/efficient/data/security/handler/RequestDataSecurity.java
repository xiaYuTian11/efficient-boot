package com.efficient.data.security.handler;

import com.efficient.common.exception.DataSecurityException;
import com.efficient.data.security.annotation.RequestDecrypt;
import com.efficient.data.security.annotation.SecuritySkip;
import com.efficient.data.security.constant.EnableType;
import com.efficient.data.security.properties.DataSecurityProperties;
import com.efficient.data.security.util.AESUtils;
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
@ConditionalOnProperty(name = "com.efficient.security.api.requestEnable", havingValue = "true")
@ControllerAdvice
public class RequestDataSecurity extends RequestBodyAdviceAdapter {
    @Autowired
    private DataSecurityProperties properties;
    @Autowired
    private AESUtils aesUtils;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        SecuritySkip skip = methodParameter.getMethodAnnotation(SecuritySkip.class);
        if (Objects.nonNull(skip) && skip.skipRequest()) {
            return false;
        }

        if (Objects.equals(properties.getApi().getRequestEnableType(), EnableType.NEED)) {
            return methodParameter.hasMethodAnnotation(RequestDecrypt.class);
        } else return Objects.equals(properties.getApi().getRequestEnableType(), EnableType.NODE);
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
            throw new DataSecurityException(e);
        }
    }
}
