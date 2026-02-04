package com.devtrack.service.impl;

import com.alibaba.fastjson2.JSON;
import com.devtrack.common.util.UserContext;
import com.devtrack.entity.OperationLog;
import com.devtrack.mapper.OperationLogMapper;
import com.devtrack.service.OpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Friday
 * @Date 2026-02-04 20:38
 */
@Service
@RequiredArgsConstructor
public class OpLogServiceImpl implements OpLogService {
    private final OperationLogMapper operationLogMapper;
    @Override
    public void log(String bizType, Long bizId, String action, Object detail) {
        OperationLog log = new OperationLog();
        log.setUserId(UserContext.getUserId());
        log.setBizType(bizType);
        log.setBizId(bizId);
        log.setAction(action);
        log.setDetail(JSON.toJSONString(detail));
        operationLogMapper.insert(log);
    }
}
