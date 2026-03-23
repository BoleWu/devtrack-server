package com.devtrack.modules.rbac.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String code;
    @NotNull(message = "角色名称不能为空")
        private String name;
    private String description;
    private Integer status;
}

