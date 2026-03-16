package com.devtrack.modules.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.user.dto.PageUserRoleDTO;
import com.devtrack.modules.user.dto.UserRoleAddDTO;
import com.devtrack.modules.user.vo.UserVO;

public interface UserRoleService {
    void addUserRole(UserRoleAddDTO userRoleAddDTO);
    void deleteUserRole(UserRoleAddDTO userRoleAddDTO);
    IPage<UserVO> getUserRoleList(PageUserRoleDTO pageUserRoleDTO);
}
