package com.devtrack.vo;


import com.devtrack.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskVO {
    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String status;
    private List<JSONDataVO> members;

    private Integer priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    public static TaskVO fromEntity(Task task) {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(task.getId());
        taskVO.setProjectId(task.getProjectId());
        taskVO.setTitle(task.getTitle());
        taskVO.setDescription(task.getDescription());
        taskVO.setStatus(task.getStatus());
        taskVO.setPriority(task.getPriority());
        taskVO.setDeadline(task.getDeadline());
        return taskVO;
    }


}
