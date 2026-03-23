package com.example.taskhub.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskhub.model.po.SysUserRolePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapperExt extends BaseMapper<SysUserRolePo> {

    List<String> listRoleCodeByUserId(@Param("userId") Long userId);
}
