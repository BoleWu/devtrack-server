package com.devtrack.modules.rbac.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Friday
 * @Date 2026-03-28 21:52
 */

@Data
public class RolePermissionDTO {
    private Long roleId;
    private List<Long> permissionIds;
}
