package com.devtrack.modules.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devtrack.modules.user.dto.PageUserRoleDTO;
import com.devtrack.modules.user.dto.UserRoleAddDTO;
import com.devtrack.modules.user.entity.UserRole;
import com.devtrack.modules.user.mapper.UserRoleMapper;
import com.devtrack.modules.user.service.UserRoleService;
import com.devtrack.modules.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleImpl implements UserRoleService {
    @Autowired
    private  UserRoleMapper userRoleMapper;
    @Override
    public void addUserRole(UserRoleAddDTO userRoleAddDTO) {
        Long roleId = userRoleAddDTO.getRoleId();
        List<Long> userIdList = userRoleAddDTO.getUserIdList();
        for (Long userId : userIdList) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            if(userRoleMapper.exists(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, roleId))){
                continue;
            }
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void deleteUserRole(UserRoleAddDTO userRoleAddDTO) {
        Long roleId = userRoleAddDTO.getRoleId();
        List<Long> userIdList = userRoleAddDTO.getUserIdList();
        for (Long userId : userIdList) {
            userRoleMapper.delete(
                new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, userId)
                    .eq(UserRole::getRoleId, roleId)
            );
        }
    }

    @Override
    public IPage<UserVO> getUserRoleList(PageUserRoleDTO pageUserRoleDTO) {
        Long roleId = pageUserRoleDTO.getRoleId();
        String name = pageUserRoleDTO.getName();
        Integer pageArg = pageUserRoleDTO.getPage();
        Integer sizeArg = pageUserRoleDTO.getLimit();
        int page = (pageArg == null || pageArg < 1) ? 1 : pageArg;
        int limit = (sizeArg == null || sizeArg < 1) ? 10 : sizeArg;
        int offset= (page - 1) * limit;
        Page<UserVO> pageData = new Page<>(page, limit);
        return userRoleMapper.queryUserRoleByList(pageData, roleId, name);
    }
}
