package com.devtrack.service;


import com.devtrack.dto.ProjectDTO;
import com.devtrack.vo.ProjectTaskStatsVO;
import com.devtrack.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    ProjectVO createProject(ProjectDTO projectDTO);
    List<ProjectVO> listMyProjects();
    ProjectVO getProjectById(Long projectId);
    ProjectTaskStatsVO getProjectStats(Long projectId);
}
