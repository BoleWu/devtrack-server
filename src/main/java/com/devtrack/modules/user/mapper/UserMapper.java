package com.devtrack.modules.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.user.entity.User;
import com.devtrack.modules.user.vo.UserListVO;
import com.devtrack.modules.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*
    * 分页查询用户
    * @param name  姓名、登录名、角色
    * @param status 状态
    * @return UserVO 用户列表
    * */
    IPage<UserListVO> userList(IPage<UserListVO> page, @Param("name") String name, @Param("status") Integer status);
}


