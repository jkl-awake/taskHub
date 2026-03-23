package com.example.taskhub.security.service;

import com.example.taskhub.model.bo.AuthLoginBo;
import com.example.taskhub.model.bo.AuthRegisterBo;
import com.example.taskhub.model.dto.AuthLoginDto;
import com.example.taskhub.model.dto.AuthRegisterDto;
import com.example.taskhub.model.vo.AuthLoginVo;
import com.example.taskhub.model.vo.CurrentUserVo;

public interface AuthService {

    CurrentUserVo register(AuthRegisterBo authRegisterBo);

    AuthLoginVo login(AuthLoginBo authLoginBo);

    CurrentUserVo getCurrentUser();
}
