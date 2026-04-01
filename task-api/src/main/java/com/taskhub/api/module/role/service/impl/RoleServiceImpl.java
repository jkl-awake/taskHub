package com.taskhub.api.module.role.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.role.bo.AddRoleBo;
import com.taskhub.api.module.role.service.RoleService;
import com.taskhub.auto.entity.SysRoleDo;
import com.taskhub.auto.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDo> implements RoleService {

    public String addRole(AddRoleBo addRoleBo){
        if(roleExists(addRoleBo.getRoleName())){
            return "角色已存在";
        }

        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();
        SysRoleDo sysRoleDo = new SysRoleDo();
        sysRoleDo.setRoleCode(uuid);
        sysRoleDo.setRoleName(addRoleBo.getRoleName());
        sysRoleDo.setRemark(addRoleBo.getRemark());
        sysRoleDo.setCreatedAt(now);
        sysRoleDo.setUpdatedAt(now);
        sysRoleDo.setDeleted(0);
        this.save(sysRoleDo);
        return uuid;
    }

    private boolean roleExists(String roleName){
        if(ObjectUtil.isEmpty(roleName)){
            return false;
        }

        return this.exists(new LambdaQueryWrapper<SysRoleDo>().eq(SysRoleDo::getRoleName, roleName));
    }
}
