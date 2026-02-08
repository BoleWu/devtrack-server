package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.User;
import com.devtrack.vo.UserListVO;
import com.devtrack.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.management.MXBean;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*
    * 分页查人员
    * @param name  姓名、登入名、角色
    * @param offset  分页偏移量
    * @param pageSize  分页大小
    * @return UserVO 人员列表
    * */
    @Select("""
            <script>
            SELECT id,username,role,name FROM user
            <if test="name != null and name !=''">
            WHERE name LIKE CONCAT('%', #{name}, '%') OR role LIKE CONCAT('%', #{name}, '%') OR username LIKE CONCAT('%', #{name}, '%')
            </if>
            ORDER BY id
            LIMIT  #{offset} , #{pageSize}
            </script>
            """)
    List<UserListVO> userList(@Param("name") String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);


    @Select("""
            <script>
            SELECT COUNT(*) as total FROM user
            <if test="name != null and name !=''">
            WHERE name LIKE CONCAT('%', #{name}, '%') OR role LIKE CONCAT('%', #{name}, '%') OR username LIKE CONCAT('%', #{name}, '%')
            </if>
            </script>
            """)
    Long userListTotal(@Param("name") String name);


}
