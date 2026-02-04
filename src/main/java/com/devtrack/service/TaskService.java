package com.devtrack.service;


import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.vo.ProjectTaskStatsVO;
import com.devtrack.vo.TaskVO;

import java.util.List;

public interface TaskService {
    List<TaskVO> getTaskByAll();
    TaskVO createTask(TaskDTO dto);
    TaskVO updateStatus(TaskStatusUpdateDTO dto);
    void assign(Long taskId, Long userId);
}
