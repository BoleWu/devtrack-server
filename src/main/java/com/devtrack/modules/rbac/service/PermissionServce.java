package com.devtrack.modules.rbac.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devtrack.modules.rbac.dto.PermissionDTO;
import com.devtrack.modules.rbac.dto.QueryPermissonDTO;
import com.devtrack.modules.rbac.entity.Permission;

public interface PermissionServce {
    String createPermission(PermissionDTO permissionDTO);
    String updatePermission(PermissionDTO permissionDTO);
    String deletePermission(Long id);
    IPage<Permission> queryPermissionList(QueryPermissonDTO queryPermissonDTO);
}
