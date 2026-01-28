package com.devtrack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to DevTrack Server!";
    }
    
    @GetMapping("/health")
    public String healthCheck() {
        return "DevTrack Server is running!";
    }
}