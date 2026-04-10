package com.devtrack;


import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ScanAllControllers {

    public static void main(String[] args) {
        // 1. 初始化 Reflections
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("com.devtrack.modules")
                .setScanners(
                        Scanners.SubTypes.filterResultsBy(s -> true),
                        Scanners.TypesAnnotated,
                        Scanners.MethodsAnnotated
                )
        );

        // 2. 自动发现 RequirePermission 注解
        Set<Class<? extends Annotation>> annotationClasses = reflections.getSubTypesOf(Annotation.class);
        Class<? extends Annotation> targetAnnotation = null;

        for (Class<? extends Annotation> clazz : annotationClasses) {
            if (clazz.getSimpleName().equals("RequirePermission")) {
                targetAnnotation = clazz;
                System.out.println("✅ 自动发现权限注解: " + clazz.getName());
                break;
            }
        }

        if (targetAnnotation == null) {
            System.err.println("❌ 未找到 RequirePermission 注解");
            return;
        }

        // 3. 获取所有带注解的方法
        Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(targetAnnotation);
        System.out.println("🔥 发现 " + annotatedMethods.size() + " 个带权限注解的方法\n");

        // 打印表头
        System.out.println(String.format("%-25s | %-25s | %-8s | %-50s | %s",
                "权限", "Code", "方法", "接口路径", "所在类"));
        System.out.println("----------------------------------------------------------------------------------------------------------");

        // 4. 遍历并解析
        for (Method method : annotatedMethods) {
            Class<?> declaringClass = method.getDeclaringClass();

            // 确保是 Controller
            if (declaringClass.isAnnotationPresent(RestController.class) ||
                    declaringClass.isAnnotationPresent(Controller.class)) {

                // --- 1. 获取权限值 ---
                com.devtrack.common.annotation.RequirePermission permissionAnnotation =
                        method.getAnnotation(com.devtrack.common.annotation.RequirePermission.class);
                String permissionValue = permissionAnnotation.value();

                // --- 2. 获取接口路径 (类路径 + 方法路径) ---
                String classPath = getClassPath(declaringClass);
                String methodPath = getMethodPath(method);
                String fullPath = combinePath(classPath, methodPath);

                // --- 3. 获取请求方法 (GET/POST) ---
                String httpMethod = getHttpMethod(method);

                // --- 4. 生成 Code (简单的反转逻辑，如 addTask -> task:add) ---
                String code = generateReversedCode(method.getName());

                // --- 5. 格式化输出 ---
                System.out.println(String.format("%-25s | %-25s | %-8s | %-50s | %s",
                        permissionValue,
                        code,
                        httpMethod,
                        fullPath,
                        declaringClass.getSimpleName() + "." + method.getName()));
            }
        }
    }

    // ================= 辅助方法 =================

    // 获取类上的 @RequestMapping 路径
    private static String getClassPath(Class<?> clazz) {
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        if (classMapping != null && classMapping.value().length > 0) {
            return classMapping.value()[0];
        }
        return "";
    }

    // 获取方法上的 @RequestMapping / @PostMapping 等路径
    private static String getMethodPath(Method method) {
        // 优先检查具体的映射注解
        if (method.isAnnotationPresent(PostMapping.class)) {
            return method.getAnnotation(PostMapping.class).value()[0];
        }
        if (method.isAnnotationPresent(GetMapping.class)) {
            return method.getAnnotation(GetMapping.class).value()[0];
        }
        if (method.isAnnotationPresent(DeleteMapping.class)) {
            return method.getAnnotation(DeleteMapping.class).value()[0];
        }
        if (method.isAnnotationPresent(PutMapping.class)) {
            return method.getAnnotation(PutMapping.class).value()[0];
        }

        // 兜底检查通用的 RequestMapping
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
        if (methodMapping != null && methodMapping.value().length > 0) {
            return methodMapping.value()[0];
        }
        return "";
    }

    // 拼接路径，处理斜杠
    private static String combinePath(String classPath, String methodPath) {
        if (classPath.isEmpty()) return methodPath;
        if (methodPath.isEmpty()) return classPath;

        if (classPath.endsWith("/") && methodPath.startsWith("/")) {
            return classPath + methodPath.substring(1);
        } else if (!classPath.endsWith("/") && !methodPath.startsWith("/")) {
            return classPath + "/" + methodPath;
        }
        return classPath + methodPath;
    }

    // 解析 HTTP 方法
    private static String getHttpMethod(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) return "GET";
        if (method.isAnnotationPresent(PostMapping.class)) return "POST";
        if (method.isAnnotationPresent(PutMapping.class)) return "PUT";
        if (method.isAnnotationPresent(DeleteMapping.class)) return "DELETE";
        if (method.isAnnotationPresent(PatchMapping.class)) return "PATCH";

        // 如果用了 @RequestMapping(method = RequestMethod.POST)
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping != null && mapping.method().length > 0) {
            return mapping.method()[0].name();
        }
        return "POST";
    }

    // 简单的 Code 反转逻辑 (例如 createTask -> task:create)
    private static String generateReversedCode(String methodName) {
        // 这是一个简单的示例，你可以根据你的命名规范调整
        // 假设方法名是驼峰命名，我们取第一个单词放到最后
        // 比如 "createTask" -> "task:create"
        // 比如 "deleteRolePermission" -> "rolePermission:delete" (这里简单处理取最后一个词)

        // 这里简单实现：找到第一个大写字母的位置
        for (int i = 1; i < methodName.length(); i++) {
            if (Character.isUpperCase(methodName.charAt(i))) {
                String prefix = methodName.substring(i); // 后面的部分
                String suffix = methodName.substring(0, i); // 前面的动作
                // 首字母小写化
                prefix = Character.toLowerCase(prefix.charAt(0)) + prefix.substring(1);
                return prefix + ":" + suffix;
            }
        }
        return methodName;
    }
}