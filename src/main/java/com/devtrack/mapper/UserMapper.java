package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.management.MXBean;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
