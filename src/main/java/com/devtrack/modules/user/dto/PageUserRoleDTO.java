package com.devtrack.modules.user.dto;

import lombok.Data;

@Data
public class PageUserRoleDTO {
    private Long roleId;
    private String name;
    private Integer page;
    private Integer limit;
}
