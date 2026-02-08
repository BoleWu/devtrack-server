package com.devtrack.controller;

import com.devtrack.common.result.R;
import com.devtrack.dto.AddProjectUserListDTO;
import com.devtrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @RequestMapping("/addMember")
    public R<?> addMember(Long projectId, Long memberId) {
        memberService.addMember(projectId, memberId);
        return R.success("成功");
    }
    @PostMapping("/addListMember")
    public R<?> addListMember(@Validated @RequestBody AddProjectUserListDTO addProjectUserListDTO) {
        System.out.println("Received projectId: " + addProjectUserListDTO.getProjectId());
        System.out.println("Received members size: " + (addProjectUserListDTO.getMembers() != null ? addProjectUserListDTO.getMembers().size() : 0));
        return R.success(memberService.addListMember(addProjectUserListDTO));
    }
}

