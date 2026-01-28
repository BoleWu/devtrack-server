package com.devtrack.service.log;

import com.devtrack.entity.log.LoginLog;

/**
 * 日志服务接口
 */
public interface LoginLogService {
    /**
     * 记录登录日志
     */
    void recordLoginLog(String username, String ip, String userAgent, boolean success, String failureReason);
}