package com.devtrack.modules.rbac.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.devtrack.modules.user.entity.UserRole;
import com.devtrack.modules.rbac.mapper.RolePermissionMapper;
import com.devtrack.modules.user.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RbacService {
    private final UserRoleMapper userRoleMapper;
    private final RolePermissionMapper rolePermissionMapper;

    public boolean hasPermission(Long userId, String permissionCode) {
        List<Long> roleIds = userRoleMapper.selectList(
                        Wrappers.<UserRole>lambdaQuery()
                                .eq(UserRole::getUserId, userId)
                )
                .stream().map(UserRole::getRoleId).toList();
        if (roleIds.isEmpty()) return false;
        int count = rolePermissionMapper.countRolePermission(roleIds, permissionCode);
        System.out.println("权限校验");
        return count > 0;
    }

}


