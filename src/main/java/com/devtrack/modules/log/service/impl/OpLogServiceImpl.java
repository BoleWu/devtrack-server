package com.devtrack.modules.log.service.impl;

import com.alibaba.fastjson2.JSON;
import com.devtrack.common.util.UserContext;
import com.devtrack.modules.log.entity.OperationLog;
import com.devtrack.modules.log.mapper.OperationLogMapper;
import com.devtrack.modules.log.service.OpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


