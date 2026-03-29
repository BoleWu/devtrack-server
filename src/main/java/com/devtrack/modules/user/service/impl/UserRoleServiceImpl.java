package com.devtrack.modules.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devtrack.modules.user.dto.PageUserRoleDTO;
import com.devtrack.modules.user.dto.UserRoleAddDTO;
import com.devtrack.modules.user.entity.UserRole;
import com.devtrack.modules.user.mapper.UserRoleMapper;
import com.devtrack.modules.user.service.UserRoleService;
import com.devtrack.modules.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
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
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(UserRoleAddDTO userRoleAddDTO) {
        Long roleId = userRoleAddDTO.getRoleId();
        List<Long> userIdList = userRoleAddDTO.getUserIdList();
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId, roleId)
                .in(UserRole::getUserId, userIdList);
        this.remove(wrapper);
    }

    @Override
    public IPage<UserVO> getUserRoleList(PageUserRoleDTO pageUserRoleDTO) {
        Long roleId = pageUserRoleDTO.getRoleId();
        String name = pageUserRoleDTO.getName();
        Integer pageArg = pageUserRoleDTO.getPage();
        Integer sizeArg = pageUserRoleDTO.getLimit();
        int page = (pageArg == null || pageArg < 1) ? 1 : pageArg;
        int limit = (sizeArg == null || sizeArg < 1) ? 10 : sizeArg;
        Page<UserVO> pageData = new Page<>(page, limit);
        return userRoleMapper.queryUserRoleByList(pageData, roleId, name);
    }
}
