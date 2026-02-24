package com.devtrack.vo;

import com.devtrack.entity.User;
import lombok.Data;

/**
 * 用户信息视图对象 - 用于返回给前端的用户数据
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String phone;
    private String name ;
    private Integer status;
    private String createTime;
    // 注意：不包含密码等敏感信息

    public static UserVO fromEntity(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setRole(user.getRole());
        userVO.setStatus(user.getStatus());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        return userVO;
    }
}