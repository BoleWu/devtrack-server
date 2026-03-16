package com.devtrack.modules.rbac.vo;

import lombok.Data;

@Data
public class RoleListVo {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer status;
}

