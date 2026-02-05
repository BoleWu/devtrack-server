package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.Project;
import com.devtrack.vo.DashboardProgressVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("""
        SELECT
            p.id,
            p.name AS projectName,
            COUNT(t.id) AS totalTasks,
            SUM(CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END) AS completedTasks,
            CASE
                WHEN COUNT(t.id) = 0 THEN 0
                ELSE CAST(SUM(CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END) * 100 / COUNT(t.id) AS UNSIGNED)
            END AS progress
        FROM project p
        LEFT JOIN task t ON t.project_id = p.id AND t.deleted = 0
        WHERE p.deleted = 0
        AND p.create_by = #{userId}
        GROUP BY p.id, p.name
        ORDER BY p.create_time DESC
        """)
    List<DashboardProgressVO> listProjectProgress(Long userId);
}
