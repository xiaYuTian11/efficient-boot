package com.efficient.data.security.util;

import com.efficient.common.constant.CommonConstant;
import com.efficient.data.security.annotation.DbEncrypted;
import com.efficient.data.security.annotation.DbFieldEncrypted;
import com.efficient.data.security.db.crypt.DbAES;
import jdk.nashorn.internal.runtime.GlobalConstants;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 反射工具类
 *
 * @author TMW
 * @since 2023/6/8 16:55
 */
public class ReflectionUtil {

    public static final Map<String, List<String>> ENCRYPT_MAP;

    static {
        ENCRYPT_MAP = initEncryptData();
    }

    /**
     * 启动获取需要加解密的数据
     */
    public static Map<String, List<String>> initEncryptData() {
        String dbEncryptModelPath = DbAES.dbEncryptModelPath;
        String[] modelPath = dbEncryptModelPath.split(CommonConstant.COMMA);
        Reflections reflections = new Reflections(modelPath);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(DbEncrypted.class);

        // 类与加密字段对应关系
        Map<String, List<String>> mapList = new HashMap<>(16 * 8);
        for (Class<?> classes : classesList) {
            // 得到该类下面的所有字段
            final String typeName = classes.getTypeName();
            List<String> list = new LinkedList<>();
            final Field[] fields = classes.getDeclaredFields();
            // 排序
            Arrays.stream(fields)
                    .filter(field -> Objects.nonNull(field.getAnnotation(DbFieldEncrypted.class)))
                    .forEach(field -> list.add(field.getName()));
            if (!list.isEmpty()) {
                mapList.put(typeName, list);
            }
        }
        return mapList;
    }
}
