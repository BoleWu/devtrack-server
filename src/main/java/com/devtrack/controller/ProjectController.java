package com.devtrack.controller;

import com.devtrack.dto.ProjectDTO;
import com.devtrack.service.ProjectService;
import com.devtrack.vo.ProjectTaskStatsVO;
import com.devtrack.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping("/createproject")
    public ProjectVO createProject(@Validated @RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }
    @GetMapping("/projects")
    public List<ProjectVO> listMyProjects() {
        return projectService.listMyProjects();
    }
    @GetMapping("/projects/{projectId}")
    public ProjectVO getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    //跳转到projects
    @GetMapping("/project")
    public String project() {
        return "redirect:/api/projects";
    }
    @GetMapping("/projects/{id}/stats")
    public ProjectTaskStatsVO projectStats(@PathVariable Long id) {
        return projectService.getProjectStats(id);
    }

}
