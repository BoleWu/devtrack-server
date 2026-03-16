package com.devtrack.modules.task.service;


import com.devtrack.modules.task.dto.TaskAddAssignDTO;
import com.devtrack.modules.task.dto.TaskDTO;
import com.devtrack.modules.task.dto.TaskStatusUpdateDTO;
import com.devtrack.modules.task.vo.BurnDownPointVO;
import com.devtrack.modules.task.vo.GanttVO;
import com.devtrack.modules.task.vo.TaskVO;

import java.util.List;

public interface TaskService {
    List<TaskVO> getTaskByAll(Long projectId);
    TaskVO createTask(TaskDTO dto);
    TaskVO updateStatus(TaskStatusUpdateDTO dto);
    void assign(Long taskId, Long userId);
    List<BurnDownPointVO> burndown(Long id);
    List<GanttVO> gantt(Long id);
    void deleteTask (Long id);
    void taskAssignee(TaskAddAssignDTO taskAddAssignDTO);
    void updateTask(TaskDTO taskDTO);
    void activateTask(TaskStatusUpdateDTO dto);
}


