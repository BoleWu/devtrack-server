package com.devtrack.modules.rbac.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devtrack.common.exception.ServiceException;
import com.devtrack.modules.rbac.dto.PermissionDTO;
import com.devtrack.modules.rbac.dto.QueryPermissonDTO;
import com.devtrack.modules.rbac.entity.Permission;
import com.devtrack.modules.rbac.mapper.PermissionMapper;
import com.devtrack.modules.rbac.service.PermissionServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerMissionImpl implements PermissionServce {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public String createPermission(PermissionDTO permissionDTO) {
        Permission permission = permissionInfo(permissionDTO);
        if (permissionMapper.exists(new LambdaQueryWrapper<Permission>().eq(Permission::getCode, permission.getCode()))){
            throw new ServiceException("权限已存在");
        }
        int insert =permissionMapper.insert(permission);
        return insert > 0 ? "添加成功" : "添加失败";
    }

    @Override
    public String deletePermission(Long id) {
            Permission permission = permissionMapper.selectById(id);
            if (permission == null){
                throw new ServiceException("权限不存在");
            }
            int update =permissionMapper.deleteById(permission);
            return update > 0 ? "删除成功" : "删除失败";
    }

    @Override
    public String updatePermission(PermissionDTO permissionDTO) {
        Permission permission = permissionInfo(permissionDTO);
        int update =permissionMapper.updateById(permission);
        return update > 0 ? "更新成功" : "更新失败";
    }
    public Permission permissionInfo(PermissionDTO permissionDTO){
        Permission permission = new Permission();
        permission.setName(permissionDTO.getName());
        permission.setCode(permissionDTO.getCode());
        permission.setDescription(permissionDTO.getDescription());
        permission.setMethod(permissionDTO.getMethod());
        permission.setPath(permissionDTO.getPath());
        return permission;
    }
    @Override
    public IPage<Permission> queryPermissionList(QueryPermissonDTO queryPermissonDTO) {
        Page<Permission> page = new Page<>(queryPermissonDTO.getPage(), queryPermissonDTO.getLimit());
        return permissionMapper.selectPage(page, new LambdaQueryWrapper<Permission>()
                .like(Permission::getName, queryPermissonDTO.getName())
                .like(Permission::getCode, queryPermissonDTO.getCode())
                .like(Permission::getPath, queryPermissonDTO.getCode())
        );
    }
}
