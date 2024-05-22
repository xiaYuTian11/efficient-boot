package com.efficient.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author TMW
 * @date 2021/3/3 11:29
 */
public class JackSonUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(JackSonUtil.class);
    public static final ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String toJson(Object o) {
        if (Objects.isNull(o)) {
            return null;
        }
        try {
            return JSON.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOGGER.error("toJson 转换异常", e);
            return "";
        }
    }

    public static <T> T toObject(String str, Class<T> cs) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            return JSON.readValue(str, cs);
        } catch (JsonProcessingException e) {
            LOGGER.error("toObject 转换异常", e);
        }
        return null;
    }

    public static <T> List<T> toObjectList(String str, Class<T> clsChild) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            JavaType javaType = JSON.getTypeFactory().constructParametricType(List.class, clsChild);
            return JSON.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            LOGGER.error("toObjectList 转换异常", e);
        }
        return null;
    }

    /**
     * 注意，此种方法返回的   java.util.LinkedHashMap
     *
     * @param str
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjectListByType(String str) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            TypeReference<List<T>> type = new TypeReference<List<T>>() {
            };
            return JSON.readValue(str, type);
        } catch (JsonProcessingException e) {
            LOGGER.error("toObjectListByType 转换异常", e);
        }
        return null;
    }

    public static <T> Map<String, T> toObjectMap(String str, Class<T> vCls) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            JavaType javaType = JSON.getTypeFactory().constructParametricType(Map.class, String.class, vCls);
            return JSON.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            LOGGER.error("toObjectMap 转换异常", e);
        }
        return null;
    }

    public static Map<String, Object> toMap(String str) {
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            return JSON.readValue(str, Map.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("toMap 转换异常", e);
        }
        return null;
    }

}
