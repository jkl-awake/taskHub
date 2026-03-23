package com.example.taskhub.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.taskhub.model.po.SysUserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapperExt extends BaseMapper<SysUserPo> {
}
