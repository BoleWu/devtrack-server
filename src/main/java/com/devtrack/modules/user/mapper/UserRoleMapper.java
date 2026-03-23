package com.devtrack.modules.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.user.entity.UserRole;
import com.devtrack.modules.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    IPage<UserVO>queryUserRoleByList(@Param("page") IPage<UserVO> page, @Param("roleId")  Long roleId , @Param("name") String name);
}


