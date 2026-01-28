package com.devtrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户信息更新数据传输对象
 */
@Data
public class UserUpdateDTO {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String role;
    private Integer status;
}