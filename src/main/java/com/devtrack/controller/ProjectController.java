package com.devtrack.controller;


import com.devtrack.common.result.R;
import com.devtrack.dto.PageInfoDTO;
import com.devtrack.dto.ProjectDTO;
import com.devtrack.dto.UpdateProject;
import com.devtrack.service.ProjectService;
import com.devtrack.service.TaskService;
import com.devtrack.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    @PostMapping("/createproject")
    public R<ProjectVO> createProject(@Validated @RequestBody ProjectDTO projectDTO) {         
        return R.success(projectService.createProject(projectDTO));
    }
    // @GetMapping("/getProjectByList")
    // public R<PageInfoVO> listMyProjects(PageInfoDTO pageInfoDTO) {
    //     return R.success(projectService.listMyProjects(pageInfoDTO));
    // }
    @PostMapping("/getProjectByList")
    public R<PageInfoVO> listMyProjectsPost(@Validated@RequestBody PageInfoDTO pageInfoDTO) {
        return R.success(projectService.listMyProjects(pageInfoDTO));
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
        return R.success(taskService.burndown(id));
    }
    @GetMapping("/{id}/gantt")
    public R<List<GanttVO>> gantt(@PathVariable Long id) {
        return R.success(taskService.gantt(id));
    }
    @GetMapping("/deleteProject")
    public R<String> deleteProject(@RequestParam Long projectId) {
        projectService.deleteProject(projectId);
        return R.success("删除成功");
    }
    @PostMapping("/updateProject")
    public R<String> updateProject(@Validated @RequestBody UpdateProject updateProject) {
        projectService.updateProject(updateProject);
        return R.success("更新成功");
    }
}
