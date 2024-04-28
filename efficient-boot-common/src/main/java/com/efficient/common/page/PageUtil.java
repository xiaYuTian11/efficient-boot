package com.efficient.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author TMW
 * @date 2021/2/25 15:45
 */
public class PageUtil<T> implements Serializable {

    private static final long serialVersionUID = 1802275390272573156L;

    public static <T> Page<T> change(long current, long size, long total, List<T> list) {
        return new Page<>(current, size, total, list);
    }

}
