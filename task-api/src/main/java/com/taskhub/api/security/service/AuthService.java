package com.taskhub.api.security.service;

import com.taskhub.api.model.bo.AuthLoginBo;
import com.taskhub.api.model.bo.AuthRegisterBo;
import com.taskhub.api.model.vo.AuthLoginVo;
import com.taskhub.api.model.vo.CurrentUserVo;

public interface AuthService {

    CurrentUserVo register(AuthRegisterBo authRegisterBo);

    AuthLoginVo login(AuthLoginBo authLoginBo);

    CurrentUserVo getCurrentUser();
}

