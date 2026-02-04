package com.devtrack.common.constant;

import org.springframework.stereotype.Component;

@Component
public class TaskStatus {
    //代办
    public static final String TODO = "TODO";
    //进行中
    public static final String DOING = "DOING";
    //已完成
    public static final String DONE = "DONE";
    //阻塞
    public static final String BLOCKED = "BLOCKED";
}
