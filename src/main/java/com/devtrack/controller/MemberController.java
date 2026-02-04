package com.devtrack.controller;

import com.devtrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @RequestMapping("/addMember")
    public void addMember(Long projectId, Long memberId) {
        memberService.addMember(projectId, memberId);
    }
}
