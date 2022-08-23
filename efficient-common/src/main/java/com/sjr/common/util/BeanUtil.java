package com.sjr.common.util;

import cn.hutool.core.date.DateUtil;
import com.sjr.common.annotation.ExportSort;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TMW
 * @since 2022/6/6 15:51
 */
@Slf4j
public class BeanUtil {

    public static List<Map<String, String>> beanListToMap(List<?> list, String format) {
        List<Map<String, String>> mapList = new ArrayList<>(list.size());
        for (Object obj : list) {
            mapList.add(beanToMap(obj, format, true));
        }
        return mapList;
    }

    public static Map<String, String> beanToMap(Object obj, String format, boolean hasNull) {
        Map<String, String> map = new LinkedHashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<Field> sortList = Arrays.stream(declaredFields).filter(field -> Objects.nonNull(field.getAnnotation(ExportSort.class)))
                .sorted(Comparator.comparingInt(field -> field.getAnnotation(ExportSort.class).value())).collect(Collectors.toList());
        sortList.forEach(field -> {
            try {
                field.setAccessible(true);
                final Object value = field.get(obj);
                if (!hasNull && Objects.isNull(value)) {
                    return;
                }
                if (field.getType() == Date.class) {
                    map.put(field.getName(), DateUtil.format((Date) value, format));
                } else {
                    map.put(field.getName(), String.valueOf(value));
                }
            } catch (IllegalAccessException e) {
                log.error("反射获取字段值异常", e);
            }
        });
        return map;
    }
}
