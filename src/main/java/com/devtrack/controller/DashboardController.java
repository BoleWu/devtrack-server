package com.devtrack.controller;

import com.devtrack.common.result.R;
import com.devtrack.service.DashboardSetvice;
import com.devtrack.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Log4j2
public class DashboardController {
    private final DashboardSetvice dashboardSetvice;
    private final TaskService taskService;
    @GetMapping("/stats")
    public R<?> dashboard() {
       return R.success(dashboardSetvice.getDashboardStats());
    }
    @GetMapping("/progress")
        public R<?> listProjectProgress(){
        return R.success(dashboardSetvice.listProjectProgress());
    }
    @GetMapping("/burn-down")
    public R<?> burndown(@RequestParam(required = false) Long id){
        return R.success(taskService.burndown(id));
    }
    @GetMapping("/gantt")
    public R<?> gantt(@RequestParam(required = false) Long id){
        return R.success(taskService.gantt(id));
    }
}
