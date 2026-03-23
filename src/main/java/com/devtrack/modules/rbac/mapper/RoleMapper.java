package com.devtrack.modules.rbac.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.rbac.entity.Role;
import com.devtrack.modules.rbac.vo.RoleListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    IPage<RoleListVo> queryRoleByList(@Param("page") IPage<RoleListVo> page, @Param("name") String name, @Param("status") Integer status);
}


