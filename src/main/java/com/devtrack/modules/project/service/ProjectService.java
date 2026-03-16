package com.devtrack.modules.project.service;


import com.devtrack.common.result.R;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.project.dto.ProjectDTO;
import com.devtrack.modules.project.dto.UpdateProject;
import com.devtrack.modules.project.vo.ProjectProgressVO;
import com.devtrack.modules.project.vo.ProjectTaskStatsVO;
import com.devtrack.modules.project.vo.ProjectVO;
import com.devtrack.modules.shared.vo.PageInfoVO;

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


