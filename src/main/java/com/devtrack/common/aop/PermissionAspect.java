package com.devtrack.common.aop;

import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.service.RbacService;
import com.devtrack.common.annotation.RequirePermission;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final RbacService rbacService;
    @Around("@annotation(requirePermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequirePermission requirePermission) throws Throwable {
        String permissionCode = requirePermission.value();
        Long userId = UserContext.getUserId(); // 当前用户
        // 校验接口权限
        boolean hasPermission = rbacService.hasPermission(userId, permissionCode);
        if (!hasPermission) {
            throw new BusinessException("无接口访问权限: " + permissionCode);
        }
        // 权限通过，继续执行
        return joinPoint.proceed();
    }
}
