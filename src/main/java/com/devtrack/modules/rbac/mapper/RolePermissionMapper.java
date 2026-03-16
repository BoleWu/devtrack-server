package com.devtrack.modules.rbac.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.modules.rbac.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    int countRolePermission(@Param("roleIds") List<Long> roleIds,
                            @Param("permissionCode") String permissionCode);

}


