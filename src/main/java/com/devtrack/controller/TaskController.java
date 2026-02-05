package com.devtrack.controller;


import com.devtrack.common.result.R;
import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
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
    public R<List<TaskVO>> listMyTasks() {
        return R.success(taskService.getTaskByAll());
    }
    @RequestMapping("/create")
    public R<TaskVO> createTask(@Validated @RequestBody TaskDTO taskDTO) {
        return R.success(taskService.createTask(taskDTO));
    }
    @RequestMapping("/status")
    public R<TaskVO> updateStatusTask(@Validated @RequestBody TaskStatusUpdateDTO dto) {
        return R.success(taskService.updateStatus(dto));
    }
    @PostMapping("/{id}/assign")
    public R<?> assignTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.assign(id, userId);
        return R.success("成功");
    }


}
