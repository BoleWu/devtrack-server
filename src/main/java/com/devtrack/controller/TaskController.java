package com.devtrack.controller;

import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.service.impl.ProjectServiceImpl;
import com.devtrack.service.impl.TaskServiceImpl;
import com.devtrack.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final  TaskServiceImpl taskService;
    @RequestMapping("/geytaskByAllList")
    public List<TaskVO> listMyTasks() {
        return taskService.getTaskByAll();
    }
    @RequestMapping("/create")
    public TaskVO createTask(@Validated @RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }
    @RequestMapping("/status")
    public TaskVO updateStatusTask(@Validated @RequestBody TaskStatusUpdateDTO dto) {
        return taskService.updateStatus(dto);
    }
    @PostMapping("/{id}/assign")
    public void assignTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.assign(id, userId);
    }


}
