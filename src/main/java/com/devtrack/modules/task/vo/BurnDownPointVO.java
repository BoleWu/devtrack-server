package com.devtrack.modules.task.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Friday
 * @Date 2026-02-04 21:20
 */

@Data
public class BurnDownPointVO {
    private LocalDate day;
    private Integer remain;
}
