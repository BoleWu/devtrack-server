package com.devtrack.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ProjectDTO {

    @NotBlank(message = "项目名称不能为空")
    private String name;

    private String description;

    // 可选：ACTIVE / DONE / ARCHIVED
    private String status;
}

