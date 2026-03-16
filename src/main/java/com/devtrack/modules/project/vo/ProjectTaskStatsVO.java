package com.devtrack.modules.project.vo;


import lombok.Data;

@Data
public class ProjectTaskStatsVO {
    private Long projectId;
    private Integer total;
    private Integer todo;
    private Integer doing;
    private Integer done;
    private Integer blocked;
    private Integer overdue;
    // 瀹屾垚鐜?%
    private Double progress;
}

