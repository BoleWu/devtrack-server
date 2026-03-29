package com.devtrack.modules.rbac.dto;

import lombok.Data;

@Data
public class QueryPermissonDTO {
    private String keyword;
    private Integer page;
    private Integer limit;
}
