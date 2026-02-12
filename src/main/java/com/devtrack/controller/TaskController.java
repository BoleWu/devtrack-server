package com.devtrack.controller;


import com.devtrack.common.result.R;
import com.devtrack.dto.TaskAddAssignDTO;
import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.service.impl.TaskServiceImpl;
import com.devtrack.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final  TaskServiceImpl taskService;
    @GetMapping("/getTaskByList")
    public R<List<TaskVO>> listMyTasks(@RequestParam(required = false) Long projectId) {
        return R.success(taskService.getTaskByAll(projectId));
    }
    @PostMapping("/createTask")
    public R<TaskVO> createTask(@Validated @RequestBody TaskDTO taskDTO) {
        System.out.println("taskDTO: " + taskDTO);
        return R.success(taskService.createTask(taskDTO));
    }
    @PostMapping("/status")
    public R<TaskVO> updateStatusTask(@Validated @RequestBody TaskStatusUpdateDTO dto) {
        return R.success(taskService.updateStatus(dto));
    }
    @PostMapping("/{id}/assign")
    public R<?> assignTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.assign(id, userId);
        return R.success("成功");
    }
    @GetMapping("/deleteTask")
    public R<?> deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return R.success("成功");
    }
    @PostMapping("/taskAssignee")
    public R<?> taskAssignee(@RequestBody TaskAddAssignDTO taskAddAssignDTO) {
        taskService.taskAssignee(taskAddAssignDTO);
        return R.success("成功");
    }
    @PostMapping("/updatetask")
    public R<?> updateTask(@RequestBody TaskDTO taskDTO) {
        taskService.updateTask(taskDTO);
        return R.success("成功");
    }
    @PostMapping("/activateTask")
    public R<?> activateTask(@RequestBody TaskStatusUpdateDTO dto) {
        taskService.activateTask(dto);
        return R.success("成功");
    }
}
