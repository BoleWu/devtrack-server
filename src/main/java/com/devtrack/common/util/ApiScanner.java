package com.devtrack.common.util;


import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口扫描工具类 (支持权限注解过滤)
 */
public class ApiScanner {

    // --- 假设这是你的自定义注解定义，实际项目中不需要这段，只需要引用即可 ---
    @interface RequirePermission {
        String value();
    }
    // -----------------------------------------------------------------------

    /**
     * 扫描指定 Controller 类，返回接口信息列表
     */
    public static List<ApiInfo> scanController(Class<?> controllerClass) {
        List<ApiInfo> apiInfos = new ArrayList<>();

        // 1. 获取类上的基础路径
        String basePath = getPathFromAnnotation(controllerClass);

        // 2. 遍历类中所有声明的方法
        for (Method method : controllerClass.getDeclaredMethods()) {
            // 忽略桥接方法、静态方法和非公开方法
            if (method.isBridge() || Modifier.isStatic(method.getModifiers()) || !Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            // --- 新增逻辑：检查 @RequirePermission 注解 ---
            RequirePermission permissionAnnotation = method.getDeclaredAnnotation(RequirePermission.class);

            // 如果方法上没有 @RequirePermission 注解，直接跳过 (不扫描)
            if (permissionAnnotation == null) {
                continue;
            }

            // 提取注解中的 value 值 (例如 "project:addMember")
            String permissionCode = permissionAnnotation.value();
            // -------------------------------------------

            // 3. 获取方法路径
            RequestMapping methodMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

            if (methodMapping != null) {
                String[] methodPaths = methodMapping.path();
                if (methodPaths.length == 0) methodPaths = methodMapping.value();

                String methodPath = methodPaths.length > 0 ? methodPaths[0] : "";

                // 4. 拼接完整路径
                String fullPath = combinePath(basePath, methodPath);

                // 5. 确定请求方法
                String httpMethod = getHttpMethod(method);

                // 6. 生成反转 Code (permission:update)
                String code = generateReversedCode(method.getName());

                // 将提取到的 permissionCode 传入 ApiInfo
                apiInfos.add(new ApiInfo(fullPath, httpMethod, method.getName(), code, permissionCode));
            }
        }
        return apiInfos;
    }

    // --- 辅助方法区域 (保持不变) ---

    private static String getPathFromAnnotation(Class<?> clazz) {
        RequestMapping reqMapping = AnnotatedElementUtils.findMergedAnnotation(clazz, RequestMapping.class);
        if (reqMapping != null) {
            String[] paths = reqMapping.path();
            if (paths.length == 0) paths = reqMapping.value();
            return paths.length > 0 ? paths[0] : "";
        }
        return "";
    }

    private static String combinePath(String base, String sub) {
        if (base.isEmpty()) return sub;
        if (sub.isEmpty()) return base;

        if (base.endsWith("/") && sub.startsWith("/")) {
            return base + sub.substring(1);
        } else if (!base.endsWith("/") && !sub.startsWith("/")) {
            return base + "/" + sub;
        }
        return base + sub;
    }

    private static String getHttpMethod(Method method) {
        if (AnnotatedElementUtils.hasAnnotation(method, GetMapping.class)) return "GET";
        if (AnnotatedElementUtils.hasAnnotation(method, PostMapping.class)) return "POST";
        if (AnnotatedElementUtils.hasAnnotation(method, PutMapping.class)) return "PUT";
        if (AnnotatedElementUtils.hasAnnotation(method, DeleteMapping.class)) return "DELETE";
        if (AnnotatedElementUtils.hasAnnotation(method, PatchMapping.class)) return "PATCH";

        RequestMapping reqMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (reqMapping != null && reqMapping.method().length > 0) {
            return reqMapping.method()[0].name();
        }
        return "POST";
    }

    private static String generateReversedCode(String methodName) {
        if (methodName == null || methodName.isEmpty()) return methodName;
        int firstUpperIndex = -1;
        for (int i = 0; i < methodName.length(); i++) {
            if (Character.isUpperCase(methodName.charAt(i))) {
                firstUpperIndex = i;
                break;
            }
        }
        if (firstUpperIndex == -1) return methodName.toLowerCase();

        String prefixOriginal = methodName.substring(firstUpperIndex);
        String prefix = Character.toLowerCase(prefixOriginal.charAt(0)) + prefixOriginal.substring(1);
        String suffix = methodName.substring(0, firstUpperIndex).toLowerCase();
        return prefix + ":" + suffix;
    }

    /**
     * 接口信息实体
     */
    public static class ApiInfo {
        public String path;
        public String method;
        public String methodName;
        public String code;
        public String permissionCode; // 新增：存储注解中的权限值

        public ApiInfo(String path, String method, String methodName, String code, String permissionCode) {
            this.path = path;
            this.method = method;
            this.methodName = methodName;
            this.code = code;
            this.permissionCode = permissionCode;
        }

        @Override
        public String toString() {
            // 格式化输出，增加了权限列
            return String.format("权限: %-25s | Code: %-25s | Path: %-40s | Type: %-6s",
                    permissionCode, code, path, method);
        }
    }
}