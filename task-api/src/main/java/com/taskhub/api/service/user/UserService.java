package com.taskhub.api.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.model.bo.UserInfoQueryBo;
import com.taskhub.api.model.vo.UserInfoQueryVo;
import com.taskhub.auto.entity.SysUserDo;

public interface UserService extends IService<SysUserDo> {

    UserInfoQueryVo me(UserInfoQueryBo userInfoQueryBo);
}

