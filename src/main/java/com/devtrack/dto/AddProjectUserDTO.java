package com.devtrack.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Friday
 * @Date 2026-02-07 21:04
 */
@Data
@NoArgsConstructor
public class AddProjectUserDTO {
    private Long userId;
    private String role;
    private Integer deleted;


    public AddProjectUserDTO(Long userId, String role, Integer deleted) {
        this.userId = userId;
        this.role = role;
        this.deleted = deleted;
    }
}
