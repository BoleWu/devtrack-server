package com.devtrack.mapper;

import com.devtrack.vo.DashboardStatsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashboardMapper {
    @Select("""
            SELECT
              	(
              	SELECT
              		count(*)
              	FROM
              		project p
              	WHERE
              		p.deleted = 0
              		AND (
              			p.create_by = #{userId}
              		OR EXISTS ( SELECT id FROM project_member pm WHERE pm.project_id = p.id AND pm.user_id = #{userId} AND pm.deleted = 0 ))
              	) AS totalProjects,
              	(
              	SELECT
              		count(*)
              	FROM
              		project p
              	WHERE
              		p.STATUS = 'ACTIVE' and p.deleted = 0
              		AND (
              			p.create_by = #{userId}
              		OR EXISTS ( SELECT id FROM project_member pm WHERE pm.project_id = p.id AND pm.user_id = #{userId} AND pm.deleted = 0 ))
              	) AS activeProjects ,
              	( SELECT COUNT(*) FROM task WHERE deleted = 0 AND assignee_id = #{userId} ) AS totalTasks,
              	( SELECT COUNT(*) FROM task WHERE deleted = 0 AND STATUS = 'DONE' AND assignee_id = #{userId} ) AS completedTasks
            """)
    DashboardStatsVO getDashboardMetrics(Long userId);
}
