package com.devtrack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "handler.interceptor")
public class InterceptorProperties {
    private boolean enable;
    private List<String> excludeUrls;
    private List<String> classNames;
}