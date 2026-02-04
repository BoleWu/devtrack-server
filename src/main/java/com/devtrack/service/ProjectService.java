package com.devtrack.service;


import com.devtrack.dto.ProjectDTO;
import com.devtrack.vo.*;

import java.util.List;

public interface ProjectService {
    ProjectVO createProject(ProjectDTO projectDTO);
    List<ProjectVO> listMyProjects();
    ProjectVO getProjectById(Long projectId);
    ProjectTaskStatsVO getProjectStats(Long projectId);
    ProjectProgressVO progress(Long projectId);
    List<BurnDownPointVO> burndown(Long projectId);
    List<GanttVO> gantt(Long id);
}
