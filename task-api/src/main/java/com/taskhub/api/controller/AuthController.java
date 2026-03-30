package com.taskhub.api.controller;

import com.taskhub.api.common.response.ApiResponse;
import com.taskhub.api.module.auth.bo.AuthLoginBo;
import com.taskhub.api.module.auth.bo.AuthRegisterBo;
import com.taskhub.api.module.auth.converter.AuthConverter;
import com.taskhub.api.module.auth.dto.AuthLoginDto;
import com.taskhub.api.module.auth.dto.AuthRegisterDto;
import com.taskhub.api.module.auth.service.AuthService;
import com.taskhub.api.module.auth.vo.AuthLoginVo;
import com.taskhub.api.module.auth.vo.CurrentUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证接口", description = "提供注册、登录和当前用户信息相关接口")
public class AuthController {

    private final AuthConverter authConverter;
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ApiResponse<CurrentUserVo> register(@Valid @RequestBody AuthRegisterDto authRegisterDto) {
        AuthRegisterBo authRegisterBo = authConverter.authRegisterDtoToBo(authRegisterDto);
        CurrentUserVo data = authService.register(authRegisterBo);
        return data != null ? ApiResponse.success(data) : ApiResponse.fail("注册失败");
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<AuthLoginVo> login(@Valid @RequestBody AuthLoginDto authLoginDto) {
        AuthLoginBo authLoginBo = authConverter.authLoginDtoToBo(authLoginDto);
        AuthLoginVo data = authService.login(authLoginBo);
        return data != null ? ApiResponse.success(data) : ApiResponse.fail("登录失败");
    }
}
