package com.devtrack.modules.user.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserRoleAddDTO {
    private Long roleId;
    private List<Long> userIdList;
}
