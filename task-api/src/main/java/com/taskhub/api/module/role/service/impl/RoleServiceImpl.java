package com.taskhub.api.module.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.role.service.RoleService;
import com.taskhub.auto.entity.SysRoleDo;
import com.taskhub.auto.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDo> implements RoleService {
}
