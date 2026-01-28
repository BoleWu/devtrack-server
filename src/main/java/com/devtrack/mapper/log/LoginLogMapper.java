package com.devtrack.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.log.LoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}