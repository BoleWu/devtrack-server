package com.devtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 * @author Friday
 */
@Data
public class TaskDTO {
    private Long id;
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
    @NotBlank(message = "任务标题不能为空")
    private String title;
    private String description;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private Integer  priority;
}

