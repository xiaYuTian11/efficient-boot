package com.efficient.configs.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 使用这个 com.efficient.common.page.PageUtil
 *
 * @author TMW
 * @date 2021/2/25 15:45
 */
@Deprecated
public class PageUtil<T> implements Serializable {

    private static final long serialVersionUID = 1802275390272573156L;

    public static <T> Page<T> change(Page<?> page, List<T> list) {
        final Page<T> newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        newPage.setRecords(list);
        return newPage;
    }

}
