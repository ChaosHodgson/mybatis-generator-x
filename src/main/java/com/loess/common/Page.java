package com.loess.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/8
 * @since 1.0
 */
public class Page<T> implements Serializable {
    private int current;//当前页
    private int rows; //当前行数
    private int offset;//偏移量，页长
    private int count;//总页数
    private int total;//数据总条数

    private List<T> records = Collections.EMPTY_LIST;

    public Page() {
        this.current = 1;
        this.offset = 10;
    }

    public Page(int current, int offset, int rows) {
        this.current = current <= 0 ? 1 : current;
        this.offset = offset == 0 ? 10 : offset;
        this.rows = rows <= 0 ? 0 : rows;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current <= 0 ? 1 : current;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return (total + offset - 1) / offset;
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

    public int getRows() {
        return rows == 0 ? (current - 1) * offset : rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
