package com.taskhub.api.controller;

import cn.hutool.core.util.ObjectUtil;
import com.taskhub.api.common.response.ApiResponse;
import com.taskhub.api.converter.user.UserConverter;
import com.taskhub.api.model.bo.UserInfoQueryBo;
import com.taskhub.api.model.dto.UserInfoQueryDto;
import com.taskhub.api.model.vo.UserInfoQueryVo;
import com.taskhub.api.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户接口", description = "查询用户信息")
public class UserController {

    private final UserConverter userConverter;
    private final UserService userService;

    /*
    * 获取当前用户信息
     *
     * @param dto 用户信息查询DTO，包含查询参数
     * @return ApiResponse<UserInfoQueryVo> 包含用户信息的响应对象
    * */
    @GetMapping("/me")
    public ApiResponse<UserInfoQueryVo> me(@Valid UserInfoQueryDto dto){
        UserInfoQueryBo bo = userConverter.userInfoDtoToBo(dto);
        UserInfoQueryVo vo = userService.me(bo);
        return ObjectUtil.isEmpty(vo) ? ApiResponse.fail("用户信息不存在") : ApiResponse.success(vo);
    }
}
