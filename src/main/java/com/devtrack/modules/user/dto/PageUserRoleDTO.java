package com.devtrack.modules.user.dto;

import lombok.Data;

@Data
public class PageUserRoleDTO {
    private Long roleId;
    private Integer page;
    private Integer limit;
    private Integer pageNum;
    private Integer pageSize;
}
