package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.dto.AddProjectUserDTO;
import com.devtrack.entity.ProjectMember;
import com.devtrack.vo.ProjectMemberVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<ProjectMember> {
    //添加项目人员
    @Insert("""
    <script>
    INSERT INTO project_member (project_id, user_id, role, create_time, deleted)
    VALUES
    <foreach collection="list" item="item" separator=",">
        (
            #{projectId},
            #{item.userId},
            #{item.role},
            NOW(),
            #{item.deleted}
        )
    </foreach>
    ON DUPLICATE KEY UPDATE
        role = VALUES(role),
        deleted = VALUES(deleted),
        create_time = IF(VALUES(deleted)=0, NOW(), create_time),
        join_time = IF(VALUES(deleted)=1, NOW(), NULL)

    </script>
    """)
    int addMember(@Param("list") List<AddProjectUserDTO> list, @Param("projectId") Long projectId);

    @Select("""
    <script>
    SELECT
        pm.project_id as id,
        pm.user_id as userId,
        u.name as name
    FROM
        project_member pm
    LEFT JOIN user u
        ON pm.user_id = u.id
    WHERE deleted = 0
    <if test="projectIds != null and !projectIds.isEmpty()">
        AND pm.project_id IN
        <foreach collection="projectIds" item="id" open="(" separator="," close=")">
            #{id}
        </foreach>
    </if>
    <if test="projectIds == null or projectIds.isEmpty()">
        AND 1 = 0
    </if>
    </script>
""")
    List<ProjectMemberVO> getProjectMemberList(@Param("projectIds") List<Long> projectIds);


        //  删除项目，所有项目对应的  deleted都为1
    @Update("""
        UPDATE project_member
        SET deleted = 1, join_time = NOW()
        WHERE project_id = #{projectId}
        """)
    void markAllMembersAsDeleted(@Param("projectId") Long projectId);
}
