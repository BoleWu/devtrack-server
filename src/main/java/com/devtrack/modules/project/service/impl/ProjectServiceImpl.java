package com.devtrack.modules.project.service.impl;


import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.project.dto.ProjectDTO;
import com.devtrack.modules.project.dto.UpdateProject;
import com.devtrack.modules.project.entity.Project;
import com.devtrack.modules.member.entity.ProjectMember;
import com.devtrack.modules.member.mapper.MemberMapper;
import com.devtrack.modules.member.vo.MemberVO;
import com.devtrack.modules.project.mapper.ProjectMapper;
import com.devtrack.modules.project.vo.ProjectProgressVO;
import com.devtrack.modules.project.vo.ProjectTaskStatsVO;
import com.devtrack.modules.project.vo.ProjectVO;
import com.devtrack.modules.shared.vo.JSONDataVO;
import com.devtrack.modules.shared.vo.PageInfoVO;
import com.devtrack.modules.task.mapper.TaskMapper;
import com.devtrack.modules.user.mapper.UserMapper;
import com.devtrack.modules.project.service.ProjectService;
import com.devtrack.modules.rbac.service.ProjectPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final MemberMapper memberMapper;
    private final ProjectPermissionService projectPermissionService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public ProjectVO createProject(ProjectDTO projectDTO) {
        Long userId = UserContext.getUserId();
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus() == null ? "ACTIVE" : projectDTO.getStatus());
        project.setCreateBy(userId);
        project.setCreateTime(LocalDateTime.now());
        project.setUpdateTime(LocalDateTime.now());
        project.setDeleted(0);
        int result = projectMapper.insert(project);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(project.getId());
        projectMember.setDeleted(0);
        projectMember.setUserId(userId);
        projectMember.setRole("ADMIN");
        projectMember.setCreateTime(LocalDateTime.now());
        projectMember.setDeleted(0);
        int  memberresult=memberMapper.insert(projectMember);
        if (result == 0 && memberresult==0) {
            throw new BusinessException("创建项目失败");
        }
        return ProjectVO.fromEntity(project);
    }

    @Override
    public PageInfoVO listMyProjects(PageInfoDTO pageInfoDTO) {
        Long userId = UserContext.getUserId();
        String name = pageInfoDTO.getName();
        Integer pageArg = pageInfoDTO.getPage() == null ? pageInfoDTO.getPageNum() : pageInfoDTO.getPage();
        Integer sizeArg = pageInfoDTO.getLimit() == null ? pageInfoDTO.getPageSize() : pageInfoDTO.getLimit();
        int page = (pageArg == null || pageArg < 1) ? 1 : pageArg;
        int limit = (sizeArg == null || sizeArg < 1) ? 10 : sizeArg;
        int offset = (page - 1) * limit;
        List<ProjectVO> list=projectMapper.getUserProjectsByList(userId,name,offset,limit);
        List<Long> projectIds = list.stream().map(ProjectVO::getId).toList();
        List<MemberVO> projectUserList=memberMapper.getProjectMemberList(projectIds);
        Map<Long, List<JSONDataVO>> memberMap = projectUserList.stream()
                .collect(Collectors.groupingBy(
                        MemberVO::getId,
                        Collectors.mapping(
                                m -> {
                                    JSONDataVO vo = new JSONDataVO();
                                    vo.setId(m.getUserId());
                                    vo.setName(m.getName());
                                    return vo;
                                },
                                Collectors.toList()
                        )
                ));
        list.forEach(projectVO -> {
            projectVO.setMembers(memberMap.get(projectVO.getId()));
        });
        Integer total = projectMapper.totalProjects(userId, name);

        return new PageInfoVO(list,total,page,limit);
    }

    @Override
    public ProjectVO getProjectById(Long projectId) {
        projectPermissionService.assertAndGetOwnerOrAdmin(projectId);
        Project project = projectMapper.selectById(projectId);
        return ProjectVO.fromEntity(project);
    }

    @Override
    public ProjectTaskStatsVO getProjectStats(Long projectId) {
        Long userId = UserContext.getUserId();
        Project project = projectMapper.selectById(projectId);
        if(project == null || project.getDeleted() == 1){
            throw new BusinessException("项目不存在");
        }
        if(!project.getCreateBy().equals(userId)){
            throw new BusinessException("无查看权限");
        }
        ProjectTaskStatsVO vo = taskMapper.statsByProject(projectId);
        vo.setProjectId(projectId);
        vo.setProgress(vo.getTotal() == 0 ? 0 : vo.getDone() * 1.0 / vo.getTotal());
        return vo;
    }

    @Override
    public ProjectProgressVO progress(Long projectId) {
        ProjectTaskStatsVO status = taskMapper.statsByProject(projectId);
        ProjectProgressVO vo = new ProjectProgressVO();
        vo.setTotal(status.getTotal());
        vo.setDone(status.getDone());
        vo.setProgress(status.getTotal() == 0 ? 0 : status.getDone() * 1.0 / status.getTotal());
        return vo;
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId){
        projectMapper.markProjectAsDeleted(projectId);
        memberMapper.markAllMembersAsDeleted(projectId);
    }

    @Override
    public void updateProject(UpdateProject updateProject){
        projectPermissionService.assertAndGetOwnerOrAdmin(updateProject.getId());
        Project project = new Project();
        project.setId(updateProject.getId());
        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setStatus(updateProject.getStatus());
        project.setUpdateTime(LocalDateTime.now());
        projectMapper.updateById(project);
    }


}


