package com.devtrack.vo;


import lombok.Data;

@Data
public class DashboardStatsVO {
    private Integer totalProjects;
    private Integer activeProjects;
    private Integer totalTasks;
    private Integer completedTasks;
}
