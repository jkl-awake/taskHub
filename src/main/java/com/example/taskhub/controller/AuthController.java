package com.example.taskhub.controller;

import com.example.taskhub.common.response.ApiResponse;
import com.example.taskhub.converter.auth.AuthConverter;
import com.example.taskhub.model.bo.AuthLoginBo;
import com.example.taskhub.model.bo.AuthRegisterBo;
import com.example.taskhub.model.dto.AuthLoginDto;
import com.example.taskhub.model.dto.AuthRegisterDto;
import com.example.taskhub.model.vo.AuthLoginVo;
import com.example.taskhub.model.vo.CurrentUserVo;
import com.example.taskhub.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Tag(name = "认证接口", description = "用户注册与登录相关接口")
public class AuthController {

    private final AuthConverter authConverter;
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ApiResponse<CurrentUserVo> register(@Valid @RequestBody AuthRegisterDto authRegisterDto){
        AuthRegisterBo authRegisterBo = authConverter.authRegisterDtoToBo(authRegisterDto);
        var data = authService.register(authRegisterBo);
        return data != null ? ApiResponse.success(data) : ApiResponse.fail("注册失败");
    }


    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<AuthLoginVo> login(@Valid @RequestBody AuthLoginDto authLoginDto){
        AuthLoginBo authLoginBo = authConverter.authLoginDtoToBo(authLoginDto);
        var data = authService.login(authLoginBo);
        return data != null ? ApiResponse.success(data) : ApiResponse.fail("登录失败");
    }
}
