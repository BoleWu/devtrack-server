package com.devtrack.config;

import com.devtrack.interceptor.AuthenticationInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * 注册身份验证拦截器
 */
@Configuration
@EnableConfigurationProperties(InterceptorProperties.class)
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final InterceptorProperties interceptorProperties;

    public InterceptorConfig(AuthenticationInterceptor authenticationInterceptor, InterceptorProperties interceptorProperties) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.interceptorProperties = interceptorProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (interceptorProperties.isEnable()) {
            // 注册身份验证拦截器，使用配置的排除路径
            registry.addInterceptor(authenticationInterceptor)
                    .excludePathPatterns(interceptorProperties.getExcludeUrls().toArray(new String[0]));
        }
    }
}