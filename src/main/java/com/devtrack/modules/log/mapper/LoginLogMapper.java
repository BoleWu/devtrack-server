package com.devtrack.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.modules.log.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}

