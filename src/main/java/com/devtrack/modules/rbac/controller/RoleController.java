package com.devtrack.modules.rbac.controller;

import com.devtrack.common.result.R;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/queryRoleBylist")
    public R<?> queryRoleBylist(@PathVariable PageInfoDTO pageInfoDTO) {
        return  R.success(roleService.queryRoleList(pageInfoDTO));

    }
}


