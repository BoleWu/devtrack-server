package com.devtrack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.util.UserContext;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.entity.Project;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.service.ProjectPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectPermissionServiceImpl implements ProjectPermissionService {
    private final ProjectMapper projectMapper;

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
        if (!project.getCreateBy().equals(userId) && !"admin".equals(role)) {
            throw new BusinessException("无权限访问");
        }
    }
}
