package com.devtrack.service;


import com.devtrack.common.result.R;
import com.devtrack.dto.PageInfoDTO;
import com.devtrack.dto.ProjectDTO;
import com.devtrack.dto.UpdateProject;
import com.devtrack.vo.*;

import java.util.List;

public interface ProjectService {
    ProjectVO createProject(ProjectDTO projectDTO);
    PageInfoVO listMyProjects(PageInfoDTO pageInfoDTO);
    ProjectVO getProjectById(Long projectId);
    ProjectTaskStatsVO getProjectStats(Long projectId);
    ProjectProgressVO progress(Long projectId);
    void deleteProject(Long projectId);
    void updateProject(UpdateProject updateProject);
}
