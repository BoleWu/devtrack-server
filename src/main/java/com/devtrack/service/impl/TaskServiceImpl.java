package com.devtrack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.constant.TaskStatusFlow;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.entity.Project;
import com.devtrack.entity.ProjectMember;
import com.devtrack.entity.Task;
import com.devtrack.mapper.MemberMapper;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.mapper.TaskMapper;
import com.devtrack.service.TaskService;
import com.devtrack.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final MemberMapper projectMemberMapper;
    private final OpLogServiceImpl OpLogServiceImpl;


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

    @Override
    public TaskVO createTask(TaskDTO dto) {
        Long userId = UserContext.getUserId();
        Project project = projectMapper.selectOne(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, dto.getProjectId())
                        .eq(Project::getCreateBy, userId)
                        .eq(Project::getDeleted, 0)
        );
        if(project == null){
            throw new BusinessException("项目不存在");
        }
        Task task = new Task();
        task.setProjectId(dto.getProjectId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() == null ? "TODO" : dto.getStatus());
        task.setCreateBy(userId);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        task.setDeleted(0);
        taskMapper.insert(task);
        return TaskVO.fromEntity(task);
    }

    @Override
    public TaskVO updateStatus(TaskStatusUpdateDTO dto) {
        Long userId = UserContext.getUserId();
        Task task = taskMapper.selectById(dto.getTaskId());
        if(task == null || task.getDeleted() == 1){
            throw new BusinessException("任务不存在");
        }
        if(!task.getCreateBy().equals(userId)){
            throw new BusinessException("无修改权限");
        }
        if(!TaskStatusFlow.canChange(task.getStatus(), dto.getNewstatus())){
            throw new BusinessException("非法状态流转: "
                    + task.getStatus() + " → " + dto.getNewstatus());
        }
        task.setStatus(dto.getNewstatus());
        task.setUpdateTime(LocalDateTime.now());
        taskMapper.updateById(task);
        return TaskVO.fromEntity(task);
    }
    @Override
    public void assign(Long taskId, Long userId) {
        Task task = taskMapper.selectById(taskId);
        checkProjectMember(task.getProjectId(), userId);
        String oldAssing = task.getAssigneeId() == null ? "" : task.getAssigneeId().toString();
        task.setAssigneeId(userId);
        taskMapper.updateById(task);
        Map<String, Object> detail = Map.of(
                "from", oldAssing,
                "to", userId
        );
        OpLogServiceImpl.log("TASK", taskId, "ASSIGN", detail);
    }
    private void checkProjectMember(Long projectId, Long userId) {
        boolean exists = projectMemberMapper.exists(
                new LambdaQueryWrapper<ProjectMember>()
                        .eq(ProjectMember::getProjectId, projectId)
                        .eq(ProjectMember::getUserId, userId)
                        .eq(ProjectMember::getDeleted, 0)
        );
        if (!exists) {
            throw new BusinessException("你不是该项目成员");
        }
    }


}
