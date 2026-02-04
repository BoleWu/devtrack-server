package com.devtrack.service;

/**
 * @author Friday
 * @Date 2026-02-04 20:37
 */
public interface OpLogService {
    void log(String bizType, Long bizId, String action, Object detail);
}
