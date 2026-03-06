package com.devtrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_role")
@Data
public class UserRole {
    private Long userId;
    private Long roleId;
}
