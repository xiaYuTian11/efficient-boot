package com.efficient.configs.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        Page page = new Page(1, 10, 24);
        ArrayList arrayList = new ArrayList();
        arrayList.add("111");
        page.setRecords(arrayList);

        com.efficient.common.page.Page change = com.efficient.common.page.PageUtil.change(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        System.out.println(change);
    }
}
