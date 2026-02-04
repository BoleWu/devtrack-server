package com.devtrack.controller;

import com.devtrack.dto.ProjectDTO;
import com.devtrack.service.ProjectService;
import com.devtrack.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping("/createproject")
    public ProjectVO createProject(@Validated @RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }
    @GetMapping("/getProjectByList")
    public List<ProjectVO> listMyProjects() {
        return projectService.listMyProjects();
    }
    @GetMapping("/{projectId}")
    public ProjectVO getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @GetMapping("/{id}/stats")
    public ProjectTaskStatsVO projectStats(@PathVariable Long id) {
        return projectService.getProjectStats(id);
    }
    @GetMapping("/{id}/progress")
    public ProjectProgressVO progress(@PathVariable Long id) {
        return projectService.progress(id);
    }
    @GetMapping("/{id}/burndown")
    public List<BurnDownPointVO> burndown(@PathVariable Long id) {
        return projectService.burndown(id);
    }
    @GetMapping("/{id}/gantt")
    public List<GanttVO> gantt(@PathVariable Long id) {
        return projectService.gantt(id);
    }
}
