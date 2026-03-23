package com.devtrack.modules.member.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.exception.BusinessException;
import com.devtrack.common.util.UserContext;
import com.devtrack.modules.member.dto.AddProjectUserDTO;
import com.devtrack.modules.member.dto.AddProjectUserListDTO;
import com.devtrack.modules.project.entity.Project;
import com.devtrack.modules.member.entity.ProjectMember;
import com.devtrack.modules.member.mapper.MemberMapper;
import com.devtrack.modules.project.mapper.ProjectMapper;
import com.devtrack.modules.member.service.MemberService;
import com.devtrack.modules.log.service.OpLogService;
import com.devtrack.modules.rbac.service.ProjectPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ProjectMapper projectMapper;
    private final OpLogService opLogService;
    private final MemberMapper memberMapper;
    private final ProjectPermissionService projectPermissionService;
    @Override
    public void addMember(Long projectId, Long memberId) {
        //判断项目是否存在
        projectPermissionService.assertAndGetOwnerOrAdmin(projectId);
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        projectMember.setUserId(memberId);
        projectMember.setRole("MEMBER");
        projectMember.setDeleted(0);
        memberMapper.insert(projectMember);
    }

    @Override
    @Transactional
    public String addListMember(AddProjectUserListDTO addProjectUserListDTO){
        //获取当前人员 ID
        Long userId = UserContext.getUserId();
        System.out.println("projectId---------->"+addProjectUserListDTO.getProjectId());
        //判断如果不是超级管理员或者项目负责人角色则返回无添加权限
        Boolean exists = projectPermissionService.checkProjectPrincipal(addProjectUserListDTO.getProjectId());
        if(!exists){
            throw new BusinessException("无权操作");
        }
        //批量更新
        List<AddProjectUserDTO> list = addProjectUserListDTO.getMembers();
        int count =memberMapper.addMember(list, addProjectUserListDTO.getProjectId());
        System.out.println("count---------->"+count);
        if (count <= 0){
            throw new BusinessException("本次未产生任何变更");
        }
        
        // 根据deleted字段区分新增和退出操？
        List<AddProjectUserDTO> addUsers = list.stream()
                .filter(user -> user.getDeleted() != null && user.getDeleted() == 0)
                .toList();
        
        List<AddProjectUserDTO> removeUsers = list.stream()
                .filter(user -> user.getDeleted() != null && user.getDeleted() == 1)
                .toList();
        
        // 记录新增人员操作日志
        if (!addUsers.isEmpty()) {
            Map<String, Object> addMap = new HashMap<>();
            List<Long> addUserIds = addUsers.stream().map(AddProjectUserDTO::getUserId).toList();
            addMap.put("users", addUserIds);
            addMap.put("deleted", 0);
            opLogService.log("PROJECT", userId, "项目成员新增", addMap);
        }
        
        // 记录退出人员操作日？
        if (!removeUsers.isEmpty()) {
            Map<String, Object> removeMap = new HashMap<>();
            List<Long> removeUserIds = removeUsers.stream().map(AddProjectUserDTO::getUserId).toList();
            removeMap.put("users", removeUserIds);
            removeMap.put("deleted", 1);
            opLogService.log("PROJECT", userId, "项目成员退出", removeMap);
        }
        
        return "操作成功";
    }



}


