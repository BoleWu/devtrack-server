package com.devtrack.modules.task.controller;


import com.devtrack.common.annotation.RequirePermission;
import com.devtrack.common.result.R;
import com.devtrack.modules.task.dto.TaskAddAssignDTO;
import com.devtrack.modules.task.dto.TaskDTO;
import com.devtrack.modules.task.dto.TaskStatusUpdateDTO;
import com.devtrack.modules.task.service.impl.TaskServiceImpl;
import com.devtrack.modules.task.vo.TaskVO;
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
    @RequirePermission("task:create")
    public R<TaskVO> createTask(@Validated @RequestBody TaskDTO taskDTO) {
        System.out.println("taskDTO: " + taskDTO);
        return R.success(taskService.createTask(taskDTO));
    }
    @PostMapping("/status")
    @RequirePermission("task:status")
    public R<TaskVO> updateStatusTask(@Validated @RequestBody TaskStatusUpdateDTO dto) {
        return R.success(taskService.updateStatus(dto));
    }
    @PostMapping("/{id}/assign")
    public R<?> assignTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.assign(id, userId);
        return R.success("成功");
    }
    @GetMapping("/deleteTask")
    @RequirePermission("task:delete")
    public R<?> deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return R.success("成功");
    }
    @PostMapping("/taskAssignee")
    @RequirePermission("task:assignee")
    public R<?> taskAssignee(@RequestBody TaskAddAssignDTO taskAddAssignDTO) {
        taskService.taskAssignee(taskAddAssignDTO);
        return R.success("成功");
    }
    @PostMapping("/updatetask")
    @RequirePermission("task:update")
    public R<?> updateTask(@RequestBody TaskDTO taskDTO) {
        taskService.updateTask(taskDTO);
        return R.success("成功");
    }
    @PostMapping("/activateTask")
    @RequirePermission("task:activate")
    public R<?> activateTask(@RequestBody TaskStatusUpdateDTO dto) {
        taskService.activateTask(dto);
        return R.success("成功");
    }
}


