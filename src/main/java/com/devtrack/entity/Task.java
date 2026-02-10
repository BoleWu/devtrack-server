package com.devtrack.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("task")
public class Task {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long projectId;

    private Long assigneeId;

    private String title;

    private String description;

    // TODO / DOING / DONE
    private String status;

    private Long createBy;

    private LocalDateTime createTime;


    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private Integer deleted;

    private Integer priority;
}
