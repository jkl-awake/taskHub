package com.taskhub.api.module.user.converter;

import com.taskhub.api.module.user.bo.UserInfoQueryBo;
import com.taskhub.api.module.user.dto.UserInfoQueryDto;
import com.taskhub.api.module.user.vo.UserInfoQueryVo;
import com.taskhub.auto.entity.SysUserDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {

    UserInfoQueryBo userInfoDtoToBo (UserInfoQueryDto getUserInfoDto);

    UserInfoQueryVo userInfoQueryDoToVo(SysUserDo sysUserDo);
}

