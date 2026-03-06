package com.devtrack.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.devtrack.entity.UserRole;
import com.devtrack.mapper.RolePermissionMapper;
import com.devtrack.mapper.UserRoleMapper;
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
        System.out.println("权限参与进来了");
        return count > 0;
    }

}
