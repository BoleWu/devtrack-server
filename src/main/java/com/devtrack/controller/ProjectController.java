package com.devtrack.controller;


import com.devtrack.common.result.R;
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
    public R<ProjectVO> createProject(@Validated @RequestBody ProjectDTO projectDTO) {         
        return R.success(projectService.createProject(projectDTO));
    }
    @GetMapping("/getProjectByList")
    public R<List<ProjectVO>> listMyProjects() {
        return R.success(projectService.listMyProjects());
    }
    @GetMapping("/{projectId}")
    public R<ProjectVO> getProjectById(@PathVariable Long projectId) {
        return R.success(projectService.getProjectById(projectId));
    }

    @GetMapping("/{id}/stats")
    public R<ProjectTaskStatsVO> projectStats(@PathVariable Long id) {
        return R.success(projectService.getProjectStats(id));
    }
    @GetMapping("/{id}/progress")
    public R<ProjectProgressVO> progress(@PathVariable Long id) {
        return R.success(projectService.progress(id));
    }
    @GetMapping("/{id}/burndown")
    public R<List<BurnDownPointVO>> burndown(@PathVariable Long id) {
        return R.success(projectService.burndown(id));
    }
    @GetMapping("/{id}/gantt")
    public R<List<GanttVO>> gantt(@PathVariable Long id) {
        return R.success(projectService.gantt(id));
    }
}
