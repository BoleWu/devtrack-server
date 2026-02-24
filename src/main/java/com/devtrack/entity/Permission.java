package com.devtrack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("permission")
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer code;
}
