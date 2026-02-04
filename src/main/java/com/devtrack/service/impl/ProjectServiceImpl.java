package com.devtrack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.dto.ProjectDTO;
import com.devtrack.entity.Project;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.mapper.TaskMapper;
import com.devtrack.service.ProjectService;
import com.devtrack.vo.ProjectTaskStatsVO;
import com.devtrack.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;

    @Override
    public ProjectVO createProject(ProjectDTO projectDTO) {
        Long userId = UserContext.getUserId();
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus() == null ? "ACTIVE" : projectDTO.getStatus());
        project.setCreateBy(userId);
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        project.setDeleted(0);
        int result = projectMapper.insert(project);
        if (result == 0) {
            throw new BusinessException("创建项目失败");
        }
        return ProjectVO.fromEntity(project);
    }

    @Override
    public List<ProjectVO> listMyProjects() {
        Long userId = UserContext.getUserId();
        List<Project> projects = projectMapper.selectList(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getCreateBy, userId)
                        .eq(Project::getDeleted, 0)
                        .orderByDesc(Project::getCreateTime)
        );
        return projects.stream().map(ProjectVO::fromEntity).toList();
    }

    @Override
    public ProjectVO getProjectById(Long projectId) {
        Project project = projectMapper.selectById(projectId);
        if(project == null || project.getDeleted() == 1){
            throw new BusinessException("项目不存在");
        }
        Long userId = UserContext.getUserId();
        String role = UserContext.getRole();
        if(!project.getCreateBy().equals(userId) && "admin".equals(role)){
            throw new BusinessException("无权限访问");
        }
        return ProjectVO.fromEntity(project);
    }

    @Override
    public ProjectTaskStatsVO getProjectStats(Long projectId) {
        Long userId = UserContext.getUserId();
        Project project = projectMapper.selectById(projectId);
        if(project == null || project.getDeleted() == 1){
            throw new BusinessException("项目不存在");
        }
        if(!project.getCreateBy().equals(userId)){
            throw new BusinessException("无查看权限");
        }
        ProjectTaskStatsVO vo = taskMapper.statsByProject(projectId);
        vo.setProjectId(projectId);
        vo.setProgress(vo.getTotal() == 0 ? 0 : vo.getDone() * 1.0 / vo.getTotal());
        return vo;
    }
}
