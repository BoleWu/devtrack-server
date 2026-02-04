package com.devtrack.vo;

import lombok.Data;

/**
 * @author Friday
 * @Date 2026-02-04 21:03
 */

@Data
public class ProjectProgressVO {
    private Integer total;
    private Integer done;
    // 完成率 %
    private Double progress;
}
