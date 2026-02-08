package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.Task;
import com.devtrack.vo.BurnDownPointVO;
import com.devtrack.vo.GanttVO;
import com.devtrack.vo.ProjectTaskStatsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    @Select("""
                SELECT
                  COUNT(*) total,
                  SUM(status = 'TODO') todo,
                  SUM(status = 'DOING') doing,
                  SUM(status = 'DONE') done,
                  SUM(status = 'BLOCKED') blocked,
                  SUM(deadline < NOW() AND status != 'DONE') overdue
                FROM task
                WHERE project_id = #{projectId}
                  AND deleted = 0
            """)
    ProjectTaskStatsVO statsByProject(Long projectId);


    @Select("""
            <script>
            SELECT DATE(create_time) day,
                   COUNT(*) remain
            FROM task
            WHERE assignee_id = #{userId}
            <if test="id != null and id !=''">
            AND project_id = #{id}
            </if>
            GROUP BY day
            ORDER BY day
            </script>
            """)
    List<BurnDownPointVO> burndown(
            @Param("id") Long id,
            @Param("userId") Long userId
    );

    @Select("""
            <script>
            SELECT id,
                   title name,
                   create_time start,
                   deadline end,
                   status
            FROM task
            WHERE assignee_id = #{userId}
            <if test="id != null and id !=''">
            AND project_id = #{id}
            </if>
            </script>
            """)
    List<GanttVO> gantt(
            @Param("id") Long id,
            @Param("userId") Long userId
    );
}
