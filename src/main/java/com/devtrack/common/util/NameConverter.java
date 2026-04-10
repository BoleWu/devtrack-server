package com.devtrack.common.util;


public class NameConverter {
    public static String toReversedColonCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        int firstUpperIndex = -1;
        // 1. 找到第一个大写字母的位置
        for (int i = 0; i < camelCase.length(); i++) {
            if (Character.isUpperCase(camelCase.charAt(i))) {
                firstUpperIndex = i;
                break;
            }
        }

        // 如果没有大写字母，无法分割
        if (firstUpperIndex == -1 || firstUpperIndex == 0) {
            return camelCase.toLowerCase();
        }

        // 2. 提取前缀（从第一个大写字母开始到结尾）
        String prefixOriginal = camelCase.substring(firstUpperIndex);

        // 3. 将前缀的首字母变为小写 (Permission -> permission)
        String prefix = Character.toLowerCase(prefixOriginal.charAt(0)) + prefixOriginal.substring(1);

        // 4. 提取后缀（动词部分，转小写）
        String suffix = camelCase.substring(0, firstUpperIndex).toLowerCase();

        return prefix + ":" + suffix;
    }
}
