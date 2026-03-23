package com.devtrack.modules.user.controller;

import com.devtrack.common.result.R;
import com.devtrack.modules.user.dto.PageUserRoleDTO;
import com.devtrack.modules.user.dto.UserRoleAddDTO;
import com.devtrack.modules.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @RequestMapping("/addUserRole")
    public R<?> addUserRole(@Validated @RequestBody UserRoleAddDTO userRoleAddDTO) {
        userRoleService.addUserRole(userRoleAddDTO);
        return R.success("角色人员添加成功");
    }
    @RequestMapping("/deleteUserRole")
    public R<?> deleteUserRole(@Validated @RequestBody UserRoleAddDTO userRoleAddDTO) {
        userRoleService.deleteUserRole(userRoleAddDTO);
        return R.success("角色人员删除成功");
    }
    @RequestMapping("/getUserRoleList")
    public R<?> getUserRoleList(@Validated @RequestBody PageUserRoleDTO pageUserRoleDTO) {
        return R.success(userRoleService.getUserRoleList(pageUserRoleDTO));
    }

}
