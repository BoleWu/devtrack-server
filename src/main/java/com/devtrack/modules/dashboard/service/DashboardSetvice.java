package com.devtrack.modules.dashboard.service;


import com.devtrack.modules.dashboard.vo.DashboardProgressVO;
import com.devtrack.modules.dashboard.vo.DashboardStatsVO;

import java.util.List;

public interface DashboardSetvice {
    DashboardStatsVO getDashboardStats();
    List<DashboardProgressVO> listProjectProgress();
}


