package com.devtrack.vo;


import com.devtrack.entity.Project;
import lombok.Data;

import java.util.List;

/**
 * 项目视图对象
 * @author Friday
 */
@Data
public class ProjectVO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String createTime;
    private String updateTime;
    private Integer memberCount;
    private String principal;
    private List<JSONDataVO> members;

    public static ProjectVO fromEntity(Project project) {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(project.getId());
        projectVO.setName(project.getName());
        projectVO.setDescription(project.getDescription());
        projectVO.setStatus(project.getStatus());
        projectVO.setCreateTime(project.getCreateTime().toString());
        projectVO.setUpdateTime(project.getUpdateTime().toString());
        return projectVO;
    }

}
