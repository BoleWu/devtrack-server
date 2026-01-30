package com.devtrack.controller;

import com.devtrack.common.result.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Log4j2
public class DashboardController {
    @RequestMapping("/home")
    public R<?> dashboard() {
        return R.ok("OK");
    }
}
