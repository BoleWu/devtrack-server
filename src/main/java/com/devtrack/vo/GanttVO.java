package com.devtrack.vo;

import lombok.Data;

/**
 * @author Friday
 * @Date 2026-02-04 21:25
 */

@Data
public class GanttVO {
    private Long id;
    private String name;
    private String start;
    private String end;
    private String status;
}