package com.devtrack.dto;


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
