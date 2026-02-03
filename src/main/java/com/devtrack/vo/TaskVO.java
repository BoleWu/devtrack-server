package com.devtrack.vo;


import com.devtrack.entity.Task;
import lombok.Data;

@Data
public class TaskVO {
    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String status;
    private Integer deleted;


    public static TaskVO fromEntity(Task task) {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(task.getId());
        taskVO.setProjectId(task.getProjectId());
        taskVO.setTitle(task.getTitle());
        taskVO.setDescription(task.getDescription());
        taskVO.setStatus(task.getStatus());
        taskVO.setDeleted(task.getDeleted());
        return taskVO;
    }
}
