package com.devtrack.modules.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.util.UserContext;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.modules.project.entity.Project;
import com.devtrack.modules.task.entity.Task;
import com.devtrack.modules.project.mapper.ProjectMapper;
import com.devtrack.modules.task.mapper.TaskMapper;
import com.devtrack.modules.rbac.service.ProjectPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectPermissionServiceImpl implements ProjectPermissionService {
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;

    @Override
    public Boolean checkProjectPrincipal(Long projectId) {
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        boolean exists = projectMapper.exists(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, projectId)
                        .eq(Project::getCreateBy, userId)
                        .eq(Project::getDeleted, 0)
        );
        return exists || "ADMIN".equals(role);
    }

    @Override
    public void assertAndGetOwnerOrAdmin(Long projectId) {
        Project project = projectMapper.selectById(projectId);
        if (project == null || project.getDeleted() == 1) {
            throw new BusinessException("项目不存在");
        }
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        if (!project.getCreateBy().equals(userId) && !"ADMIN".equals(role)) {
            throw new BusinessException("无权限访问");
        }
    }

    @Override
    public Boolean chekckTask(Long taskId){
        Long userId = UserContext.getUserId();
        Task task =taskMapper.selectById(taskId);
        Project project = projectMapper.selectById(task.getProjectId());
        return userId.equals(project.getCreateBy()) || userId.equals(task.getCreateBy()) || userId.equals(task.getAssigneeId());
    }
}


