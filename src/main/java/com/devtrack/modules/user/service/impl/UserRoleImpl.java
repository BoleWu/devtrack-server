package com.devtrack.modules.user.service.impl;


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
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void deleteUserRole(UserRoleAddDTO userRoleAddDTO) {
        Long roleId = userRoleAddDTO.getRoleId();
        List<Long> userIdList = userRoleAddDTO.getUserIdList();
        for (Long userId : userIdList) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleMapper.deleteById(userRole);
        }
    }

    @Override
    public IPage<UserVO> getUserRoleList(PageUserRoleDTO pageUserRoleDTO) {
        Long roleId = pageUserRoleDTO.getRoleId();
        Integer pageArg = pageUserRoleDTO.getPage() == null ? pageUserRoleDTO.getPageNum() : pageUserRoleDTO.getPage();
        Integer sizeArg = pageUserRoleDTO.getLimit() == null ? pageUserRoleDTO.getPageSize() : pageUserRoleDTO.getLimit();
        int page = (pageArg == null || pageArg < 1) ? 1 : pageArg;
        int limit = (sizeArg == null || sizeArg < 1) ? 10 : sizeArg;
        int offset= (page - 1) * limit;
        Page<UserVO> pageData = new Page<>(offset, limit);
        return userRoleMapper.queryUserRoleByList(pageData, roleId);
    }
}
