package com.devtrack.modules.dashboard.service.impl;

import com.devtrack.common.util.UserContext;
import com.devtrack.modules.dashboard.mapper.DashboardMapper;
import com.devtrack.modules.project.mapper.ProjectMapper;
import com.devtrack.modules.dashboard.service.DashboardSetvice;
import com.devtrack.modules.dashboard.vo.DashboardProgressVO;
import com.devtrack.modules.dashboard.vo.DashboardStatsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardSetviceImpl implements DashboardSetvice {
    private final DashboardMapper dashboardMapper;
    private final ProjectMapper projectMapper;
    @Override
   public DashboardStatsVO getDashboardStats(){
        Long userId = UserContext.getUserId();
        return dashboardMapper.getDashboardMetrics(userId);
    }
    @Override
    public List<DashboardProgressVO> listProjectProgress(){
        Long userId = UserContext.getUserId();
        return projectMapper.listProjectProgress(userId);
    }

}


