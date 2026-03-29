package com.devtrack.modules.rbac.controller;

import com.devtrack.common.annotation.RequirePermission;
import com.devtrack.common.result.R;
import com.devtrack.modules.rbac.dto.RoleDTO;
import com.devtrack.modules.rbac.dto.RoleStatuUpdateDTO;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/queryRoleBylist")
    public R<?> queryRoleBylist(@Validated @RequestBody PageInfoDTO pageInfoDTO) {
        return  R.success(roleService.queryRoleList(pageInfoDTO));
    }
    @RequestMapping("/createRole")
    @RequirePermission("role:create")
    public R<?> createRole(@Validated @RequestBody RoleDTO roleDTO) {
        roleService.createRole(roleDTO);
        return  R.success("角色新增成功");
    }
    @RequestMapping("/updateRoleStatus")
    @RequirePermission("role:updateStatus")
    public R<?> updateRoleStatus(@Validated @RequestBody RoleStatuUpdateDTO roleStatuUpdateDTO) {
        roleService.updateRoleStatus(roleStatuUpdateDTO);
        return  R.success("角色状态更新成功");
    }
    @GetMapping("/getRoleById")
    public R<?> getRoleById(@RequestParam Long id) {
        return  R.success(roleService.getRoleById(id));
    }
    @RequestMapping("/deleteRole")
    @RequirePermission("role:delete")
    public R<?> deleteRole(@RequestParam Long id) {
        roleService.deleteRole(id);
        return  R.success("角色删除成功");
    }
    @RequestMapping("/updateRole")
    @RequirePermission("role:update")
    public R<?> updateRole(@Validated @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);
        return  R.success("角色更新成功");
    }
}


