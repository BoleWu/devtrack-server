package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.Project;
import com.devtrack.vo.DashboardProgressVO;
import com.devtrack.vo.JSONDataVO;
import com.devtrack.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    //项目任务进度
    @Select("""
            SELECT
              p.id,
              p.name AS projectName,
              p.create_by AS createId,
              p.create_time AS createTime,
              COALESCE(stats.totalTasks, 0) AS totalTasks,
              COALESCE(stats.completedTasks, 0) AS completedTasks,
              CASE
                WHEN stats.totalTasks IS NULL OR stats.totalTasks = 0 THEN 0
                ELSE (stats.completedTasks * 100) DIV stats.totalTasks
              END AS progress
            FROM project p
            LEFT JOIN (
              SELECT
                t.project_id,
                COUNT(*) AS totalTasks,
                SUM(CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END) AS completedTasks
              FROM task t
              WHERE t.deleted = 0
              GROUP BY t.project_id
            ) AS stats ON stats.project_id = p.id
            WHERE p.deleted = 0
              AND p.status = 'ACTIVE'
              AND (
                p.create_by = #{userId}
                OR EXISTS (
                  SELECT 1
                  FROM project_member pm
                  WHERE pm.project_id = p.id
                    AND pm.user_id = #{userId}
                    AND pm.deleted = 0
                )
              )
            ORDER BY p.update_time DESC
            """)
    List<DashboardProgressVO> listProjectProgress(Long userId);

    //    查询项目列表
    @Select("""
            <script>
            SELECT
                p.create_time AS createTime,
                p.id AS id,
                p.description AS description,
                p.name AS name,
                p.update_time AS updateTime,
                p.status AS status,
                p.create_by AS createId,
                u.name AS principal,
                (
                  SELECT COUNT(*)
                  FROM project_member pm
                  WHERE pm.project_id = p.id AND pm.deleted = 0
                ) AS memberCount
            FROM project p
            LEFT JOIN user u ON u.id = p.create_by
            WHERE p.deleted = 0
              AND (
                p.create_by = #{userId}
                OR EXISTS (
                  SELECT 1 FROM project_member pm
                  WHERE pm.project_id = p.id
                    AND pm.user_id = #{userId}
                    AND pm.deleted = 0
                )
              )
            <if test="name != null and name != ''">
              AND p.name LIKE CONCAT('%', #{name}, '%')
            </if>
            ORDER BY p.id DESC
            LIMIT #{offset} , #{pageSize}
            </script>
            """)
    List<ProjectVO> getUserProjectsByList(@Param("userId")Long userId,@Param("name") String  name , @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

        @Select("""
            <script>
            SELECT COUNT(*)
            FROM project p
            WHERE p.deleted = 0
              AND (
                p.create_by = #{userId}
                OR EXISTS (
                  SELECT 1 FROM project_member pm
                  WHERE pm.project_id = p.id
                    AND pm.user_id = #{userId}
                    AND pm.deleted = 0
                )
              )
            <if test="name != null and name != ''">
              AND p.name LIKE CONCAT('%', #{name}, '%')
            </if>
            </script>
            """)
    Integer totalProjects(@Param("userId")Long userId,@Param("name") String  name);

    //删除项目
    @Update("UPDATE project SET deleted = 1 WHERE id = #{projectId}")
    void markProjectAsDeleted(@Param("projectId") Long projectId);

}
