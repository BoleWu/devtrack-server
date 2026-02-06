package com.devtrack.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devtrack.entity.ProjectMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<ProjectMember> {


}
