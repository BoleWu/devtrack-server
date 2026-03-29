package com.devtrack.modules.rbac.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String method;
    private String path;
}
