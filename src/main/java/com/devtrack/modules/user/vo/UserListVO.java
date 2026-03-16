package com.devtrack.modules.user.vo;

import lombok.Data;
import java.util.List;

/**
 * 用户列表视图对象
 */
@Data
public class UserListVO {
    private Long id;
    private String username;
    private String role;
    private String name;
    private Integer status;
}
