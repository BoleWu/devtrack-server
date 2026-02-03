package com.devtrack.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("task")
public class Task {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private String title;

    private String description;

    private String status; // TODO / DOING / DONE

    private Long createBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer deleted;
}
