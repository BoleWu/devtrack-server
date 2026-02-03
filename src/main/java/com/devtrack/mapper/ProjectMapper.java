package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.Project;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
