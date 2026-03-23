package com.devtrack.modules.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    * @param offset  分页偏移量
    * @param pageSize  分页大小
    * @return UserVO 用户列表
    * */
    @Select("""
            <script>
            SELECT id,username,role,name,status,email,phone FROM user
            <where>
            <if test="name != null and name !=''">
            and name LIKE CONCAT('%', #{name}, '%') OR role LIKE CONCAT('%', #{name}, '%') OR username LIKE CONCAT('%', #{name}, '%')
            </if>
            <if test="status !=null">
                and status = #{status}
            </if>
            </where>
            ORDER BY id
            LIMIT  #{offset} , #{pageSize}
            </script>
            """)
    List<UserListVO> userList(@Param("name") String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize ,@Param("status") Integer status);


    @Select("""
            <script>
            SELECT COUNT(*) as total FROM user
            <where>
            <if test="name != null and name !=''">
            and name LIKE CONCAT('%', #{name}, '%') OR role LIKE CONCAT('%', #{name}, '%') OR username LIKE CONCAT('%', #{name}, '%')
            </if>
                <if test="status !=null">
                and status = #{status}
            </if>
            </where>
            </script>
            """)
    Long userListTotal(@Param("name") String name , @Param("status") Integer status);


}


