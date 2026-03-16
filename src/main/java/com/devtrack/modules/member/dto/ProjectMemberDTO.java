package com.devtrack.modules.member.dto;

import com.devtrack.modules.shared.dto.JSONDataDTO;
import lombok.Data;

import java.util.List;

/**
 * @author Friday
 * @Date 2026-02-08 01:12
 */
@Data
public class ProjectMemberDTO {
    private Long id;
    private List<JSONDataDTO> members;

}

