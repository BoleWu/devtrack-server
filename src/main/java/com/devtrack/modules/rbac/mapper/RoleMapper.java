package com.devtrack.modules.rbac.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.rbac.entity.Role;
import com.devtrack.modules.rbac.vo.RoleListVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    IPage<RoleListVo> queryRoleByList(IPage<RoleListVo> page, String name);
}


