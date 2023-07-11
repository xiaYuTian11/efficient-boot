package com.efficient.data.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.efficient.data.security.annotation.RequestDecrypt;
import com.efficient.data.security.annotation.SecuritySkip;
import com.efficient.data.security.constant.EnableType;
import com.efficient.data.security.properties.DataSecurityProperties;
import com.efficient.data.security.util.AESUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author TMW
 * @since 2023/7/5 15:41
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private final Map<String, String[]> parameterMap = new HashMap<>();
    private final AESUtils aesUtils;
    private final Method method;

    public RequestWrapper(HttpServletRequest request, AESUtils aesUtils, DataSecurityProperties properties, Method method) {
        super(request);
        // 将body数据存储起来
        String bodyStr = getBodyString(request);
        body = bodyStr.getBytes(StandardCharsets.UTF_8);
        this.aesUtils = aesUtils;
        this.method = method;
        // get 数据加解密
        if (StrUtil.equals(request.getMethod(), "GET")) {
            SecuritySkip skip = method.getAnnotation(SecuritySkip.class);
            if (Objects.nonNull(skip) && skip.skipRequest()) {
                return;
            }

            if (Objects.equals(properties.getApi().getRequestEnableType(), EnableType.NEED) && Objects.nonNull(method.getAnnotation(RequestDecrypt.class))) {
                this.decryptParam();
            } else if (Objects.equals(properties.getApi().getRequestEnableType(), EnableType.NODE)) {
                this.decryptParam();
            }
        }
    }

    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public void decryptParam() {
        Set<String> keySet = super.getParameterMap().keySet();
        for (String key : keySet) {
            Map<String, String[]> urlParams = null;
            urlParams = this.getUrlParams(aesUtils.decrypt(key));
            parameterMap.putAll(urlParams);
        }
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("", e);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将url参数转换成map
     *
     * @param param 参数
     * @return java.util.Map<java.lang.String, java.lang.String [ ]>
     * @date 2022年10月20日 18时00分
     */
    public Map<String, String[]> getUrlParams(String param) {
        Map<String, String[]> paramMap = new LinkedHashMap<>(10);
        if (org.springframework.util.StringUtils.isEmpty(param)) {
            return paramMap;
        }
        String[] params = param.split("&");
        for (String paramStr : params) {
            String[] p = paramStr.split("=");
            if (p.length == 2) {
                String key = p[0];
                if (paramMap.containsKey(key)) {
                    paramMap.put(key, CollUtil.newArrayList(paramMap.get(key), URLUtil.decode(p[1])).toArray(new String[]{}));
                } else {
                    paramMap.put(key, new String[]{URLUtil.decode(p[1])});
                }
            }
        }
        return paramMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = super.getParameterValues(name);
        if (result == null && this.parameterMap.containsKey(name)) {
            result = this.parameterMap.get(name);
        }
        return result;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public int available() throws IOException {
                return inputStream.available();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
