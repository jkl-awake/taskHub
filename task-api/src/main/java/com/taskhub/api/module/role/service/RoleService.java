package com.taskhub.api.module.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.module.role.bo.AddRoleBo;
import com.taskhub.auto.entity.SysRoleDo;

public interface RoleService extends IService<SysRoleDo> {

    String addRole(AddRoleBo addRoleBo);
}
