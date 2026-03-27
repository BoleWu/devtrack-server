package com.devtrack.modules.rbac.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private String code;
    private String name;
    private String description;
    private String url;
    private String method;
    private String path;
}
