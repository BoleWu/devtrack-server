package com.devtrack.vo;


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
    // 完成率 %
    private Double progress;
}
