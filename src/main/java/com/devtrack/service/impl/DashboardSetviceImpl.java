package com.devtrack.service.impl;

import com.devtrack.common.util.UserContext;
import com.devtrack.mapper.DashboardMapper;
import com.devtrack.mapper.ProjectMapper;
import com.devtrack.service.DashboardSetvice;
import com.devtrack.vo.DashboardProgressVO;
import com.devtrack.vo.DashboardStatsVO;
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
