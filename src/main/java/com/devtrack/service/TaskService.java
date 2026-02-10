package com.devtrack.service;


import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.vo.BurnDownPointVO;
import com.devtrack.vo.GanttVO;
import com.devtrack.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskService {
    List<TaskVO> getTaskByAll(Long projectId);
    TaskVO createTask(TaskDTO dto);
    TaskVO updateStatus(TaskStatusUpdateDTO dto);
    void assign(Long taskId, Long userId);
    List<BurnDownPointVO> burndown(Long id);
    List<GanttVO> gantt(Long id);
    void deleteTask (Long id);
}
