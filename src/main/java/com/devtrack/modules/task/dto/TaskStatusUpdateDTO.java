package com.devtrack.modules.task.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    @NotNull
    private Long taskId;
    @NotBlank
    private String newstatus;
}

