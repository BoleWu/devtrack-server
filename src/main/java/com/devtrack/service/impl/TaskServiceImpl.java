package com.devtrack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.constant.TaskStatusFlow;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.dto.TaskAddAssignDTO;
import com.devtrack.dto.TaskDTO;
import com.devtrack.dto.TaskStatusUpdateDTO;
import com.devtrack.entity.Project;
import com.devtrack.entity.ProjectMember;
import com.devtrack.entity.Task;
import com.devtrack.entity.TaskMember;
import com.devtrack.mapper.MemberMapper;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.mapper.TaskMapper;
import com.devtrack.mapper.TaskMemberMapper;
import com.devtrack.service.TaskService;
import com.devtrack.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final MemberMapper projectMemberMapper;
    private final OpLogServiceImpl OpLogServiceImpl;
    private final ProjectPermissionServiceImpl projectPermissionService;
    private final TaskMemberMapper taskMemberMapper;


    @Override
    public List<TaskVO> getTaskByAll(Long projectId) {
        Long userId = UserContext.getUserId();
        List<Task> tasks = taskMapper.selectList(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getProjectId, projectId)
                        .eq(Task::getCreateBy, userId)
                        .eq(Task::getDeleted, 0)
                        .orderByDesc(Task::getId)
        );
        List<TaskVO> tasksList = tasks.stream().map(TaskVO::fromEntity).toList();
        List<Long> taskIds = tasksList.stream().map(TaskVO::getId).toList();
        List<MemberVO> TaskUserList=taskMemberMapper.getTaskMemberList(taskIds);
        Map<Long, List<JSONDataVO>> memberMap = TaskUserList.stream()
                .collect(Collectors.groupingBy(
                        MemberVO::getId,
                        Collectors.mapping(
                                m -> {
                                    JSONDataVO vo = new JSONDataVO();
                                    vo.setId(m.getUserId());
                                    vo.setName(m.getName());
                                    return vo;
                                },
                                Collectors.toList()
                        )
                ));
        tasksList.forEach(TaskVO -> {
            TaskVO.setMembers(memberMap.get(TaskVO.getId()));
        });
//        return tasks.stream().map(TaskVO::fromEntity).toList();
        return tasksList;
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
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTitle, dto.getTitle())
                .eq(Task::getProjectId, dto.getProjectId())
                .eq(Task::getDeleted, 0);
        if(taskMapper.exists(wrapper)){
            throw new BusinessException("任务已存在");
        }
        Task task = new Task();
        task.setProjectId(dto.getProjectId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() == null ? "TODO" : dto.getStatus());
        task.setCreateBy(userId);
        task.setPriority(dto.getPriority() == null ? 0 : dto.getPriority());
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        task.setDeleted(0);
        task.setDeadline(dto.getDeadline());
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
    @Override
    public List<BurnDownPointVO> burndown(Long projectId) {
        Long userId = UserContext.getUserId();
        List<BurnDownPointVO> list=taskMapper.burndown(projectId,userId);
        log.debug("list: {}", list);
        return  list;
    }
    @Override
    public List<GanttVO> gantt( Long id){
        Long userId = UserContext.getUserId();
        return taskMapper.gantt(id,userId);
    }

    @Override
    public void deleteTask (Long id){
        if(!projectPermissionService.chekckTask(id)){
            throw new BusinessException("无删除权限");
        }
        taskMapper.restore(id);
    }
    @Override
    @Transactional
    public void taskAssignee(TaskAddAssignDTO taskAddAssignDTO){
        Long taskId = taskAddAssignDTO.getTaskId();
        List<Long> list = taskAddAssignDTO.getList();
        for (Long id : list){
            TaskMember taskMember = new TaskMember();
            taskMember.setTaskId(taskId);
            taskMember.setUserId(id);
            taskMemberMapper.insert(taskMember);
        }
    }
}
