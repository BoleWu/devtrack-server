package com.devtrack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.util.UserContext;
import com.devtrack.entity.Project;
import com.devtrack.entity.Task;
import com.devtrack.mapper.TaskMapper;
import com.devtrack.service.TeskService;
import com.devtrack.vo.ProjectVO;
import com.devtrack.vo.TaskVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TeskService {
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskVO> getTaskByAll() {
        Long userId = UserContext.getUserId();
        List<Task> tasks = taskMapper.selectList(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getCreateBy, userId)
                        .eq(Task::getDeleted, 0)
                        .orderByDesc(Task::getCreateTime)
        );
        return tasks.stream().map(TaskVO::fromEntity).toList();
    }


}
