package com.devtrack.service.impl.log;

import com.devtrack.entity.log.LoginLog;
import com.devtrack.mapper.log.LoginLogMapper;
import com.devtrack.service.log.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {
    
    private final LoginLogMapper loginLogMapper;
    
    @Override
    public void recordLoginLog(String username, String ip, String userAgent, boolean success, String failureReason) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        loginLog.setSuccess(success);
        loginLog.setFailureReason(failureReason);
        loginLog.setCreateTime(LocalDateTime.now());
        
        loginLogMapper.insert(loginLog);
        
        // 同时打印日志到控制台
        String logMessage = String.format(
            "用户 %s 于 %s %s", 
            username, 
            LocalDateTime.now().toString().replace('T', ' '), 
            success ? "登录成功" : ("登录失败: " + (failureReason != null ? failureReason : ""))
        );
        System.out.println(logMessage);
    }
}