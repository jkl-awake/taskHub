package com.taskhub.api.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.module.user.bo.UserInfoQueryBo;
import com.taskhub.api.module.user.vo.UserInfoQueryVo;
import com.taskhub.auto.entity.SysUserDo;

public interface UserService extends IService<SysUserDo> {
    UserInfoQueryVo me(UserInfoQueryBo userInfoQueryBo);
}

