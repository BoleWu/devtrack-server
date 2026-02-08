package com.devtrack.service;



import com.devtrack.dto.AddProjectUserListDTO;



public interface MemberService {
    void addMember(Long projectId, Long memberId);
    String addListMember(AddProjectUserListDTO addProjectUserListDTO);
}
