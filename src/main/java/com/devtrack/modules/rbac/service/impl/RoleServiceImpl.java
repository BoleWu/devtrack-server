package com.devtrack.modules.rbac.service.impl;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devtrack.modules.rbac.dto.RoleDTO;
import com.devtrack.modules.rbac.dto.RoleStatuUpdateDTO;
import com.devtrack.modules.rbac.entity.Role;
import com.devtrack.modules.rbac.vo.RoleVO;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.rbac.mapper.RoleMapper;
import com.devtrack.modules.rbac.service.RoleService;
import com.devtrack.modules.rbac.vo.RoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public IPage<RoleListVo> queryRoleList(PageInfoDTO pageInfoDTO){
        String name = pageInfoDTO.getName();
        Integer status = (Integer) pageInfoDTO.getStatus();
        Integer pageArg = pageInfoDTO.getPage();
        Integer sizeArg = pageInfoDTO.getLimit();
        int page = (pageArg == null || pageArg < 1) ? 1 : pageArg;
        int limit = (sizeArg == null || sizeArg < 1) ? 10 : sizeArg;
        Page<RoleListVo> pageData = new Page<>(page, limit);
        return roleMapper.queryRoleByList(pageData, name,status);
    }
    @Override
    public void createRole(RoleDTO roleDTO){
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setCode(roleDTO.getCode());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.getStatus());
        roleMapper.insert(role);
    }

    @Override
    public void updateRoleStatus(RoleStatuUpdateDTO roleStatuUpdateDTO) {
        Long id = roleStatuUpdateDTO.getRoleId();
        Integer status = roleStatuUpdateDTO.getStatus();
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        roleMapper.updateById(role);
    }

    @Override
    public RoleVO getRoleById(Long id) {
        Role role = roleMapper.selectById(id);
        RoleVO roleVO = new RoleVO();
        if (role != null) {
            roleVO.setId(role.getId());
            roleVO.setName(role.getName());
            roleVO.setCode(role.getCode());
            roleVO.setDescription(role.getDescription());
            roleVO.setStatus(role.getStatus());
            roleVO.setCreateTime(role.getCreateTime());
            roleVO.setUpdateTime(role.getUpdateTime());
        }
        return roleVO;
    }

    @Override
    public void deleteRole(Long id) {
        roleMapper.deleteById(id);
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setCode(roleDTO.getCode());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.getStatus());
        roleMapper.updateById(role);
    }
}


