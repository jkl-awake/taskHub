package com.taskhub.api.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.service.role.UserRoleService;
import com.taskhub.auto.entity.SysUserRoleDo;
import com.taskhub.auto.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleDo> implements UserRoleService {
}
