package com.taskhub.api.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.service.role.RoleService;
import com.taskhub.auto.entity.SysRoleDo;
import com.taskhub.auto.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDo> implements RoleService {
}
