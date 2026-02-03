package com.devtrack.controller;

import com.devtrack.dto.ProjectDTO;
import com.devtrack.service.ProjectService;
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
    @PostMapping("/projects")
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
}
