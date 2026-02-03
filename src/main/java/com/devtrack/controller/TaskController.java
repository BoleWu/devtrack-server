package com.devtrack.controller;

import com.devtrack.service.impl.TaskServiceImpl;
import com.devtrack.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private TaskServiceImpl taskService;
    @RequestMapping("/tasks")
    public List<TaskVO> listMyTasks() {
        return taskService.getTaskByAll();
    }
}
