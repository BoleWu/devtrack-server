package com.devtrack.modules.rbac.service;

import com.devtrack.modules.rbac.dto.RolePermissionByIdDTO;
import com.devtrack.modules.rbac.dto.RolePermissionDTO;
import com.devtrack.modules.rbac.entity.RolePermission;

import java.util.List;

/**
 * @author Friday
 * @Date 2026-03-28 21:49
 */
public interface RolePermissionService {
    String createRolePermission(RolePermissionDTO rolePermissionDTO);
    String deleteRolePermission(RolePermissionDTO rolePermissionDTO);
    List<RolePermission> queryRolePermissionList(Long roleId);
    String createRolePermissionById(RolePermissionByIdDTO rolePermissionDTO);
}
