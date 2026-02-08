package com.devtrack.dto;

import lombok.Data;

/**
 * @author Friday
 * @Date 2026-02-07 19:12
 */
@Data
public class PageInfoDTO {
    private String name;
    private Integer page;
    private Integer limit;
    private Integer pageNum;
    private Integer pageSize;
}
