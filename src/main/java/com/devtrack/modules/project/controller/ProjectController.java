package com.devtrack.modules.project.controller;


import com.devtrack.common.annotation.RequirePermission;
import com.devtrack.common.result.R;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.project.dto.ProjectDTO;
import com.devtrack.modules.project.dto.UpdateProject;
import com.devtrack.modules.project.service.ProjectService;
import com.devtrack.modules.task.service.TaskService;
import com.devtrack.modules.project.vo.ProjectProgressVO;
import com.devtrack.modules.project.vo.ProjectTaskStatsVO;
import com.devtrack.modules.project.vo.ProjectVO;
import com.devtrack.modules.shared.vo.PageInfoVO;
import com.devtrack.modules.task.vo.BurnDownPointVO;
import com.devtrack.modules.task.vo.GanttVO;
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
    @RequirePermission("project:create")
    public R<ProjectVO> createProject(@Validated @RequestBody ProjectDTO projectDTO) {         
        return R.success(projectService.createProject(projectDTO));
    }
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
    @RequirePermission("project:delete")
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


