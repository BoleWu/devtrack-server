package com.devtrack.dto;

import lombok.Data;

/**
 * @author Friday
 * @Date 2026-02-08 14:52
 */

@Data
public class UpdateProject {
    private String name;
    private String description;
    private String status;
    private Long id;
}
