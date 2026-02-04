package com.devtrack.common.constant;


import java.util.Map;
import java.util.Set;

public class TaskStatusFlow {
    private static final Map<String, Set<String>> FLOW =Map.of(
            TaskStatus.TODO, Set.of(TaskStatus.DOING, TaskStatus.BLOCKED),
            TaskStatus.DOING, Set.of(TaskStatus.DOING,TaskStatus.BLOCKED),
            TaskStatus.BLOCKED, Set.of(TaskStatus.DOING),
            TaskStatus.DONE, Set.of()
    );
    public static boolean canChange(String from, String to) {
        return FLOW.getOrDefault(from, Set.of()).contains(to);
    }
}
