package com.devtrack.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Friday
 * @Date 2026-02-07 21:14
 */
@Data
public class AddProjectUserListDTO {
    private List<AddProjectUserDTO> members;
    private Long projectId;
}
