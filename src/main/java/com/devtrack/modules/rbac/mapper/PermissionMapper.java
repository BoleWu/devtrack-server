package com.devtrack.modules.rbac.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.modules.rbac.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
