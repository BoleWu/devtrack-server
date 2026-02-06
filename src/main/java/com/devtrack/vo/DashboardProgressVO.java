package com.devtrack.vo;

import lombok.Data;

@Data
public class DashboardProgressVO {
    private Long id;
    private String projectName;
    private Integer totalTasks;
    private Integer progress;
    private Integer completedTasks;
}
