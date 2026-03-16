package com.devtrack.modules.project.vo;

import lombok.Data;

/**
 * @author Friday
 * @Date 2026-02-04 21:03
 */

@Data
public class ProjectProgressVO {
    private Integer total;
    private Integer done;
    // 瀹屾垚鐜?%
    private Double progress;
}

