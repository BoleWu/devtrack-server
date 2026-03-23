package com.devtrack.modules.rbac.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devtrack.modules.rbac.dto.RoleDTO;
import com.devtrack.modules.rbac.dto.RoleStatuUpdateDTO;
import com.devtrack.modules.rbac.vo.RoleVO;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.rbac.vo.RoleListVo;
import org.apache.ibatis.annotations.Param;


public interface RoleService {
     IPage<RoleListVo> queryRoleList(PageInfoDTO pageInfoDTO);
     void createRole(RoleDTO roleDTO);
     void updateRole(RoleDTO roleDTO);
     void deleteRole(Long id);
     RoleVO getRoleById(Long id);
     void updateRoleStatus(RoleStatuUpdateDTO roleStatuUpdateDTO);
}


