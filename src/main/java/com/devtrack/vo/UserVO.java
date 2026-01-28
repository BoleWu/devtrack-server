package com.devtrack.vo;

import lombok.Data;

/**
 * 用户信息视图对象 - 用于返回给前端的用户数据
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String role;
    private Integer status;
    private String createTime;
    // 注意：不包含密码等敏感信息
}