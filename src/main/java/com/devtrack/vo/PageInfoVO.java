package com.devtrack.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Friday
 * @Date 2026-02-07 19:47
 */
@Data
public class PageInfoVO {
    private List<?> data;
    private long total;
    private int pageNum;
    private int pageSize;
    private int totalPages;

    public PageInfoVO(List<?> data, long total, int pageNum, int pageSize) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages =  (int) Math.ceil((double) total / pageSize);
    }
}
