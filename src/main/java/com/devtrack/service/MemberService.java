package com.devtrack.service;


public interface MemberService {
    void addMember(Long projectId, Long memberId);
    void assignTask(Long projectId, Long memberId);
}
