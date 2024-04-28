package com.efficient.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TMW
 * @since 2022/2/24 18:01
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -7102129155309986923L;

    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;
    protected long pages;

    public Page(long current, long size, long total, List<T> records) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    private long getPages() {
        if (this.getSize() == 0L) {
            return 0L;
        } else {
            long pages = this.getTotal() / this.getSize();
            if (this.getTotal() % this.getSize() != 0L) {
                ++pages;
            }

            return pages;
        }
    }

}
