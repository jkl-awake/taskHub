package com.taskhub.api.module.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.role.service.UserRoleService;
import com.taskhub.auto.entity.SysUserRoleDo;
import com.taskhub.auto.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleDo> implements UserRoleService {
}
