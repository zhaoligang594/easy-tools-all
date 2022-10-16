package vip.breakpoint.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询的基本操作
 */
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 5341200631975152081L;
    private int prePage; // 上一页
    private int nextPage; // 下一页
    private int currentPage = 1; // 当前页 default 1
    private int pageSize = 10; // 页面大小  default 10
    private int dataStart; // 数据查询到开始位置
    private int dataEnd; // 数据查询的结束位置
    private int pageTotal; // 总共多少页
    private int totalCount; // 总共多少条
    private List<T> data; //返回的数据

    // 设置我们的页面的大小
    public final void setPageEndByPageSize(int pageSize) {
        this.pageSize = pageSize;
        int start = (currentPage - 1) * pageSize + 1;
        int end = (currentPage) * pageSize;
        this.dataStart = start;
        this.dataEnd = end;
    }

    // 设置总页面的大小
    public final void setPageTotalByTotalCount(int totalCount) {
        this.totalCount = totalCount;
        int pages = totalCount / this.pageSize + 1;
        this.pageTotal = pages;
        if (this.currentPage == pages) {
            nextPage = pages;
        } else {
            nextPage = currentPage + 1;
        }
    }// end method

    // generate by idea IDE
    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDataStart() {
        return dataStart;
    }

    public void setDataStart(int dataStart) {
        this.dataStart = dataStart;
    }

    public int getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(int dataEnd) {
        this.dataEnd = dataEnd;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", data=" + data +
                '}';
    }

}
