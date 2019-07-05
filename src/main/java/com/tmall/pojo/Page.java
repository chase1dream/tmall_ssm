package com.tmall.pojo;

/**
 * @author tanquan
 * @description <p>
 * 商品信息分页展示实体类
 * </p>
 * @date 2019/7/2 11:49
 */
public class Page {
    private static final int defaultCount = 5; //默认每页显示5条
    private int start; //开始页数
    private int count; //每页显示个数
    private int total; //总个数
    private String param; //参数

    public Page() {
        count = defaultCount;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    // 判断是否还有上一页
    public boolean isHasPreviouse() {
        if (start == 0) {
            return false;
        }
        return true;
    }

    // 判断是否还有下一页
    public boolean isHasNext() {
        if (start == getLast()) {
            return false;
        }
        return true;
    }

    // 计算总页数
    // 根据每页显示的数量count以及总共有多少条数据total，计算出总共有多少页
    public int getTotalPage() {
        int totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if (0 == total % count) {
            totalPage = total / count;
        }
        // 假设总数是51，不能够被5整除的，那么就有11页
        else {
            totalPage = total / count + 1;
        }

        if (0 == totalPage)
            totalPage = 1;
        return totalPage;
    }

    // 获取最后一页
    public int getLast() {
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count) {
            last = total - count;
        }
        // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else {
            last = total - total % count;
            last = last < 0 ? 0 : last;
        }
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }
}
