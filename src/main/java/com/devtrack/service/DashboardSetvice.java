package com.devtrack.service;


import com.devtrack.vo.DashboardProgressVO;
import com.devtrack.vo.DashboardStatsVO;

import java.util.List;

public interface DashboardSetvice {
    DashboardStatsVO getDashboardStats();
    List<DashboardProgressVO> listProjectProgress();
}
