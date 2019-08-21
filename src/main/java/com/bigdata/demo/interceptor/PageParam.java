package com.bigdata.demo.interceptor;

public class PageParam {
    private int page;
    private int pageSize;
    private boolean useFlag;
    private boolean checkFlag;
    private int total ;
    private int totalPage;


    public PageParam(int page, int pageSize, boolean useFlag, boolean checkFlag, int total, int totalPage) {
        this.page = page;
        this.pageSize = pageSize;
        this.useFlag = useFlag;
        this.checkFlag = checkFlag;
        this.total = total;
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
