package com.devtrack.vo;

import lombok.Data;

/**
 * 登录响应视图对象
 */
@Data
public class LoginVO {
    private String token;
    private String username;
}