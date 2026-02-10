package com.devtrack.service;

import com.devtrack.entity.Project;

public interface ProjectPermissionService {
    Boolean checkProjectPrincipal(Long projectId);
    void assertAndGetOwnerOrAdmin(Long projectId);
    Boolean chekckTask(Long taskId);
}
