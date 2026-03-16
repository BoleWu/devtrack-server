package com.devtrack.modules.task.dto;


import lombok.Data;

import java.util.List;

@Data
public class TaskAddAssignDTO {
    private Long taskId;
    private List<Long> list;
}

