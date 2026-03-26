package com.taskhub.api.converter.user;

import com.taskhub.api.model.bo.UserInfoQueryBo;
import com.taskhub.api.model.dos.UserInfoQueryQo;
import com.taskhub.api.model.dto.UserInfoQueryDto;
import com.taskhub.api.model.vo.UserInfoQueryVo;
import com.taskhub.auto.entity.SysUserDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {

    UserInfoQueryBo userInfoDtoToBo (UserInfoQueryDto getUserInfoDto);

    UserInfoQueryVo userInfoQueryDoToVo(SysUserDo sysUserDo);
}

