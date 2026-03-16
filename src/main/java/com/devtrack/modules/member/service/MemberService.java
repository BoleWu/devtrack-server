package com.devtrack.modules.member.service;



import com.devtrack.modules.member.dto.AddProjectUserListDTO;



public interface MemberService {
    void addMember(Long projectId, Long memberId);
    String addListMember(AddProjectUserListDTO addProjectUserListDTO);
}


