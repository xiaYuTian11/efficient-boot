package com.sjr.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author TMW
 * @since 2022/2/24 18:01
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -7102129155309986923L;

    private List<T> list;                // list result of this page
    private int pageNum;                // page number
    private int pageSize;                // result amount of this page
    private int totalPage;                // total page
    private int totalRow;                // total row

    /**
     * Constructor.
     *
     * @param list       the list of paginate result
     * @param pageNum the page number
     * @param pageSize   the page size
     * @param totalPage  the total page of paginate
     * @param totalRow   the total row of paginate
     */
    public Page(List<T> list, int pageNum, int pageSize, int totalPage, int totalRow) {
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
    }

    public Page() {

    }

    /**
     * Return list of this page.
     */
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * Return page number.
     */
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Return page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Return total page.
     */
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Return total row.
     */
    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public boolean isFirstPage() {
        return pageNum == 1;
    }

    public boolean isLastPage() {
        return pageNum >= totalPage;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("pageNumber : ").append(pageNum);
        msg.append("\npageSize : ").append(pageSize);
        msg.append("\ntotalPage : ").append(totalPage);
        msg.append("\ntotalRow : ").append(totalRow);
        return msg.toString();
    }
}
