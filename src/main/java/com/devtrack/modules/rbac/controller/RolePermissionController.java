package com.devtrack.modules.rbac.controller;

import com.devtrack.common.annotation.RequirePermission;
import com.devtrack.common.result.R;
import com.devtrack.modules.rbac.dto.RolePermissionByIdDTO;
import com.devtrack.modules.rbac.dto.RolePermissionDTO;
import com.devtrack.modules.rbac.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Friday
 * @Date 2026-03-28 23:42
 */
@RestController
@RequestMapping("/api/rolePermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @RequestMapping("/createRolePermission")
    @RequirePermission("rolePermission:create")
    public R <?> createRolePermission(@Validated @RequestBody RolePermissionDTO rolePermissionDTO){
        return R.success(rolePermissionService.createRolePermission(rolePermissionDTO));
    }
    @RequestMapping("/deleteRolePermission")
    @RequirePermission("rolePermission:delete")
    public R <?> deleteRolePermission(@Validated @RequestBody RolePermissionDTO rolePermissionDTO){
        return R.success(rolePermissionService.deleteRolePermission(rolePermissionDTO));
    }
    @GetMapping("/queryRolePermission")
    public R <?> queryRolePermission(@RequestParam  Long roleId ){
        return R.success(rolePermissionService.queryRolePermissionList(roleId));
    }
    @PostMapping("/createRolePermissionById")
    @RequirePermission("rolePermission:createById")
    public R <?> createRolePermission(@Validated @RequestBody RolePermissionByIdDTO rolePermissionDTO){
        return R.success(rolePermissionService.createRolePermissionById(rolePermissionDTO));
    }
}
