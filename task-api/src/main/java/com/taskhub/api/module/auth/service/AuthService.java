package com.taskhub.api.module.auth.service;

import com.taskhub.api.module.auth.bo.AuthLoginBo;
import com.taskhub.api.module.auth.bo.AuthRegisterBo;
import com.taskhub.api.module.auth.vo.AuthLoginVo;
import com.taskhub.api.module.auth.vo.CurrentUserVo;

public interface AuthService {

    CurrentUserVo register(AuthRegisterBo authRegisterBo);

    AuthLoginVo login(AuthLoginBo authLoginBo);

    CurrentUserVo getCurrentUser();
}

