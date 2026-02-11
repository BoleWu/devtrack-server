package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.TaskMember;
import com.devtrack.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMemberMapper extends BaseMapper<TaskMember> {
    @Select("""
            <script>
            SELECT
                tm.task_id as id,
                tm.user_id as userId,
                u.name as name
            FROM
                task_member tm
            LEFT JOIN user u
                ON tm.user_id = u.id
            WHERE deleted = 0
            <if test="taskIds != null and !taskIds.isEmpty()">
                AND tm.task_id IN
                <foreach collection="taskIds" item="id" open="(" separator="," close=")">
                    #{id}
                </foreach>
            </if>
            <if test="taskIds == null or taskIds.isEmpty()">
                AND 1 = 0
            </if>
            </script>
            """)
    List<MemberVO> getTaskMemberList(@Param("taskIds") List<Long> taskIds);
}
