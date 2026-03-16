package com.devtrack.modules.rbac.service;

import com.devtrack.modules.project.entity.Project;

public interface ProjectPermissionService {
    Boolean checkProjectPrincipal(Long projectId);
    void assertAndGetOwnerOrAdmin(Long projectId);
    Boolean chekckTask(Long taskId);
}


