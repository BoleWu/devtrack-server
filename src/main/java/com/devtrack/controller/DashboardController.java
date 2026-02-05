package com.devtrack.controller;

import com.devtrack.common.result.R;
import com.devtrack.service.DashboardSetvice;
import com.devtrack.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Log4j2
public class DashboardController {
    private final DashboardSetvice dashboardSetvice;
    private final ProjectService projectService;
    @GetMapping("/stats")
    public R<?> dashboard() {
       return R.success(dashboardSetvice.getDashboardStats());
    }
    @GetMapping("/progress")
        public R<?> listProjectProgress(){
        return R.success(dashboardSetvice.listProjectProgress());
    }
    @GetMapping("/burn-down")
    public R<?> burndown(Long projectId){
        return R.success(projectService.burndown(projectId));
    }
    @GetMapping("/gantt")
    public R<?> gantt(Long projectId){
        return R.success(projectService.gantt(projectId));
    }
}
