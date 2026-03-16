package com.devtrack.modules.log.service;

import com.devtrack.modules.log.entity.LoginLog;

/**
 * 日志服务接口
 */
public interface LoginLogService {
    /**
     * 记录登录日志
     */
    void recordLoginLog(String username, String ip, String userAgent, boolean success, String failureReason);
}

