package com.devtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TaskDTO {
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
    @NotBlank(message = "任务标题不能为空")
    private String title;
    private String description;
    private String status;
}
