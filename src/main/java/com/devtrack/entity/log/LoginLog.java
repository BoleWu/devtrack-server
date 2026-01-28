package com.devtrack.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志实体类
 */
@Data
@TableName("login_log")
public class LoginLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;          // 用户名
    
    private String ip;               // IP地址
    
    private String userAgent;        // 用户代理
    
    private Boolean success;         // 是否登录成功
    
    private String failureReason;    // 失败原因（如果失败）
    
    private LocalDateTime createTime; // 创建时间
}