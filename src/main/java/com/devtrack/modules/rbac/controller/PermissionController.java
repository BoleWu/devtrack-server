package com.devtrack.modules.rbac.controller;

import com.devtrack.common.result.R;
import com.devtrack.modules.rbac.dto.PermissionDTO;
import com.devtrack.modules.rbac.dto.QueryPermissonDTO;
import com.devtrack.modules.rbac.service.PermissionServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionServce permissionServce;
    @RequestMapping("/queryPermissionByList")
    public R<?> queryPermissionList( @Validated  @RequestBody QueryPermissonDTO queryPermissonDTO){
            return R.success(permissionServce.queryPermissionList(queryPermissonDTO));
    }
    @RequestMapping("/createPermission")
    public R<?> createPermission(@Validated  @RequestBody  PermissionDTO permissionDTO){
        return R.success(permissionServce.createPermission(permissionDTO));
    }
    @RequestMapping("/updatePermission")
    public R<?> updatePermission(@Validated  @RequestBody  PermissionDTO permissionDTO){
        return R.success(permissionServce.updatePermission(permissionDTO));
    }
    @GetMapping("/deletePermission")
    public R<?> deletePermission(@RequestParam Long id){
        return R.success(permissionServce.deletePermission(id));
    }
}
