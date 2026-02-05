package com.devtrack.mapper;

import com.devtrack.vo.DashboardStatsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashboardMapper {
    @Select("""
        SELECT
          (SELECT COUNT(*) FROM project WHERE deleted = 0 AND create_by = #{userId}) AS totalProjects,
          (SELECT COUNT(*) FROM project WHERE deleted = 0 AND status = 'ACTIVE' AND create_by = #{userId}) AS activeProjects,
          (SELECT COUNT(*) FROM task WHERE deleted = 0 AND  (create_by = #{userId} OR assignee_id =#{userId})) AS totalTasks,
          (SELECT COUNT(*) FROM task WHERE deleted = 0 AND status = 'DONE' AND (create_by = #{userId} OR assignee_id =#{userId})) AS completedTasks
        """)
    DashboardStatsVO getDashboardMetrics(Long userId);
}
