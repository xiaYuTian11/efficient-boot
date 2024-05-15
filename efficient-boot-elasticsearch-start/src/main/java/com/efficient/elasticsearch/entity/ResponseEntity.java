package com.efficient.elasticsearch.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * es 相应参数解析实体
 *
 * @author TMW
 * @since 2021/7/5 17:14
 */
@Data
public class ResponseEntity {

    public static final ResponseEntity EMPTY_OBJ = new ResponseEntity();

    private List<Map<String, String>> columns;
    private List<List<Object>> rows;

    public List<Map<String, Object>> toMap() {
        final List<Map<String, String>> columns = this.columns;
        final List<List<Object>> rows = this.rows;
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (CollUtil.isEmpty(columns) || columns.size() < 1
                || CollUtil.isEmpty(rows) || rows.size() < 1) {
            return mapList;
        }
        rows.forEach(list -> {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                String key = columns.get(i).get("name");
                Object value = list.get(i);
                map.put(key, value);
            }
            mapList.add(map);
        });
        return mapList;
    }

    public <T> List<T> toBean(Class<T> tClass) {
        final List<Map<String, String>> columns = this.columns;
        final List<List<Object>> rows = this.rows;
        List<T> mapList = new ArrayList<>();
        if (CollUtil.isEmpty(columns) || columns.size() < 1
                || CollUtil.isEmpty(rows) || rows.size() < 1) {
            return mapList;
        }
        rows.forEach(list -> {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                String key = columns.get(i).get("name");
                Object value = list.get(i);
                map.put(key, value);
            }
            mapList.add(BeanUtil.toBeanIgnoreError(map, tClass));
        });
        return mapList;
    }

}
