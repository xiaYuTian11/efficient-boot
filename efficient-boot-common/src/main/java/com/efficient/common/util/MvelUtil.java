package com.efficient.common.util;

import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

/**
 * 表达式计算
 *
 * @author TMW
 * @since 2024/4/12 14:24
 */
public class MvelUtil {
    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("a", 5.6d);
        dataMap.put("b", 6.6d);
        dataMap.put("c", 11.0d);
        System.out.println(MvelUtil.compare(dataMap, "a+c==b"));
        double compute = MvelUtil.compute(dataMap, "a+b");
        System.out.println(compute);
    }

    /**
     * 计算规则是否匹配
     *
     * @param dataMap    数据
     * @param expression 规则
     * @return
     */
    public static boolean compare(Map<String, Object> dataMap, String expression) {
        return (boolean) MVEL.eval(expression, dataMap);
    }

    /**
     * 根据规则计算值
     *
     * @param dataMap    数据
     * @param expression 规则
     * @return
     */
    public static <T> T compute(Map<String, Object> dataMap, String expression) {
        return (T) MVEL.eval(expression, dataMap);
    }
}
