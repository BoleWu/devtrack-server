package com.devtrack.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devtrack.common.exception.ServiceException;
import com.devtrack.modules.rbac.dto.RolePermissionByIdDTO;
import com.devtrack.modules.rbac.dto.RolePermissionDTO;
import com.devtrack.modules.rbac.entity.Permission;
import com.devtrack.modules.rbac.entity.RolePermission;
import com.devtrack.modules.rbac.mapper.PermissionMapper;
import com.devtrack.modules.rbac.mapper.RolePermissionMapper;
import com.devtrack.modules.rbac.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolePermissionImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteRolePermission(RolePermissionDTO rolePermissionDTO) {
        if (rolePermissionDTO.getPermissionIds() == null || rolePermissionDTO.getPermissionIds().isEmpty()) {
            throw new ServiceException("请选择权限");
        }
        if (rolePermissionDTO.getRoleId() == null) {
            throw new ServiceException("角色 ID 不能为空");
        }
        Long roleId = rolePermissionDTO.getRoleId();
        List<Long> permissionIds = rolePermissionDTO.getPermissionIds();
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId)
                .in(RolePermission::getPermissionId, permissionIds);
        this.remove(wrapper);
        return "删除成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createRolePermission(RolePermissionDTO rolePermissionDTO) {
        // 验证角色 ID 是否有效
        if (rolePermissionDTO.getRoleId() == null) {
            throw new ServiceException("角色 ID 不能为空");
        }
        List<RolePermission> rolePermissionList = createRolePermissionList(rolePermissionDTO);
        boolean b = this.saveBatch(rolePermissionList);
        return b ? "添加成功" : "添加失败";
    }

    public List<RolePermission> createRolePermissionList(RolePermissionDTO rolePermissionDTO) {
        if (rolePermissionDTO.getPermissionIds() == null || rolePermissionDTO.getPermissionIds().isEmpty()) {
            throw new ServiceException("请选择权限");
        }
        Long roleId = rolePermissionDTO.getRoleId();
        List<Long> permissionIds = rolePermissionDTO.getPermissionIds();
        //先删除改角色的权限
        if (rolePermissionMapper.exists(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId))) {
            LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RolePermission::getRoleId, roleId);
            this.remove(wrapper);
        }
        // 验证所有权限 ID 是否存在
        validatePermissionIds(permissionIds);
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        return rolePermissionList;
    }

    @Override
    public String createRolePermissionById(RolePermissionByIdDTO dto) {
        Long roleId = dto.getRoleId();
        Long permissionId = dto.getPermissionId();
        if (roleId == null || permissionId == null) {
            throw new ServiceException("角色 ID 和权限 ID 不能为空");
        }
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        boolean b = this.save(rolePermission);
        return b ? "添加成功" : "添加失败";
    }


    @Override
    public List<RolePermission> queryRolePermissionList(Long roleId) {
        if (roleId == null) {
            throw new ServiceException("角色 ID 不能为空");
        }
        return this.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
    }

    /**
     * 验证权限 ID 列表中的所有 ID 是否都存在
     */
    private void validatePermissionIds(List<Long> permissionIds) {
        // 查询数据库中实际存在的权限 ID
        List<Long> existingIds = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                        .in(Permission::getId, permissionIds))
                .stream()
                .map(Permission::getId)
                .toList();

        // 找出不存在的权限 ID
        Set<Long> existingIdSet = new HashSet<>(existingIds);
        List<Long> notFoundIds = permissionIds.stream()
                .filter(id -> !existingIdSet.contains(id))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new ServiceException("以下权限 ID 不存在：" + String.join(", ", notFoundIds.stream().map(String::valueOf).collect(Collectors.toList())));
        }
    }
}
