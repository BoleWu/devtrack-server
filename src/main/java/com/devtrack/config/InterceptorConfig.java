package com.devtrack.config;

import com.devtrack.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * 注册身份验证拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    public InterceptorConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册身份验证拦截器，排除不需要验证的路径
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns("/api/auth/login")      // 登录接口
                .excludePathPatterns("/api/auth/register")   // 注册接口
                .excludePathPatterns("/health")              // 健康检查
                .excludePathPatterns("/error")               // 错误页面
                .excludePathPatterns("/");                   // 主页
    }
}