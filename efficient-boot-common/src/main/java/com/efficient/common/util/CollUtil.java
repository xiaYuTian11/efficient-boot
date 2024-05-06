package com.efficient.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author TMW
 * @since 2024/1/8 9:51
 */
public class CollUtil {
    /**
     * 切割集合
     *
     * @param list
     * @param batchSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int batchSize) {
        return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
                .collect(Collectors.toList());
    }

    /**
     * 反转map
     *
     * @param originalMap
     * @return
     */
    public static Map<String, String> reverseMap(Map<String, String> originalMap) {
        Map<String, String> reversedMap = new HashMap<>();
        if (cn.hutool.core.collection.CollUtil.isEmpty(originalMap)) {
            return reversedMap;
        }

        for (Map.Entry<String, String> entry : originalMap.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }
}
