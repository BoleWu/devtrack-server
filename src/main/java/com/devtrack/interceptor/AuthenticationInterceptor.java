package com.devtrack.interceptor;

import com.devtrack.common.util.JwtUtil;
import com.devtrack.common.util.UserContext;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

/**
 * 身份验证拦截器
 * 用于验证JWT令牌的有效性
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Authorization头
        log.info("开始了----------------------");
        log.info("url---------------------->{}",request.getRequestURI());
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        log.info("authHeader-------->{}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // 移除 "Bearer " 前缀

            try {
                // 提取用户名
                String username = jwtUtil.extractUsername(token);
                // 验证令牌
                if (jwtUtil.validateToken(token, username)) {
                    // 令牌有效，允许继续执行
                    Long userId = jwtUtil.extractUserId(token);
                    String role = jwtUtil.extractRole(token);
                    log.info("上下文注入 userId={}, role={}", userId, role);
                    UserContext.set(userId, role);
                    return true;
                }
            } catch (ExpiredJwtException e) {
                // 令牌过期或其他异常
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return false;
            }
        }

        // 没有令牌或令牌无效，拒绝访问
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Access denied. Please provide a valid token.");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.clear();
    }
}