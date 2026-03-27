package com.devtrack.modules.rbac.controller;

import com.devtrack.common.result.R;
import com.devtrack.modules.rbac.dto.PermissionDTO;
import com.devtrack.modules.rbac.dto.QueryPermissonDTO;
import com.devtrack.modules.rbac.service.PermissionServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionServce permissionServce;
    @RequestMapping("/queryPermissionByList")
    public R<?> queryPermissionList(QueryPermissonDTO queryPermissonDTO){
            return R.success(permissionServce.queryPermissionList(queryPermissonDTO));
    }
    @RequestMapping("/createPermission")
    public R<?> createPermission(PermissionDTO permissionDTO){
        return R.success(permissionServce.createPermission(permissionDTO));
    }
    @RequestMapping("/updatePermission")
    public R<?> updatePermission(PermissionDTO permissionDTO){
        return R.success(permissionServce.updatePermission(permissionDTO));
    }
    @RequestMapping("/deletePermission")
    public R<?> deletePermission(Long id){
        return R.success(permissionServce.deletePermission(id));
    }
}
