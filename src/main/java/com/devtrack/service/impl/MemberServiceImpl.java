package com.devtrack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.entity.Project;
import com.devtrack.entity.ProjectMember;
import com.devtrack.mapper.MemberMapper;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ProjectMapper projectMapper;
    private final MemberMapper memberMapper;
    @Override
    public void addMember(Long projectId, Long memberId) {
        Long userId = UserContext.getUserId();
        Project project = projectMapper.selectById(projectId);
        if(!project.getCreateBy().equals(userId)){
            throw new BusinessException("无添加权限");
        }
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        projectMember.setUserId(memberId);
        projectMember.setRole("MEMBER");
        projectMember.setDeleted(0);
        memberMapper.insert(projectMember);
    }


    public void checkProjectMember(Long projectId, Long userId) {
        boolean exists = memberMapper.exists(new LambdaQueryWrapper<ProjectMember>()
                .eq(ProjectMember::getProjectId, projectId)
                .eq(ProjectMember::getUserId, userId)
                .eq(ProjectMember::getDeleted, 0)
        );
        if (!exists){
            throw new BusinessException("你不是该项目成员");
        }
    }
}
