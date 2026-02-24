package com.devtrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("permission_user")
@Data
public class PermissionUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long permissionId;
    private Long userId;
}
