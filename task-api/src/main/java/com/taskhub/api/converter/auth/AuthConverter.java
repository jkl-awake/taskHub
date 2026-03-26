package com.taskhub.api.converter.auth;

import com.taskhub.api.model.bo.AuthLoginBo;
import com.taskhub.api.model.bo.AuthRegisterBo;
import com.taskhub.api.model.dto.AuthLoginDto;
import com.taskhub.api.model.dto.AuthRegisterDto;
import com.taskhub.api.model.vo.CurrentUserVo;
import com.taskhub.auto.entity.SysRoleDo;
import com.taskhub.auto.entity.SysUserDo;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AuthConverter {

    AuthRegisterBo authRegisterDtoToBo(AuthRegisterDto authRegisterDto);

//    default SysUserEntity authRegisterBoToEntity(AuthRegisterBo authRegisterBo, PasswordEncoder passwordEncoder, LocalDateTime now){
//        return SysUserEntity.builder()
//                .username(authRegisterBo.getUsername())
//                .password(passwordEncoder.encode(authRegisterBo.getPassword()))
//                .nickname(StringUtils.hasText(authRegisterBo.getNickname()) ? authRegisterBo.getNickname() : authRegisterBo.getUsername())
//                .email(authRegisterBo.getEmail())
//                .status(1)
//                .createdAt(now)
//                .updatedAt(now)
//                .deleted(0)
//                .build()
//                ;
//    }

    default CurrentUserVo sysUserPoToCurrentUserVo(SysUserDo sysUserPo, List<SysRoleDo> rolePos){
        return CurrentUserVo.builder()
                .id(sysUserPo.getId())
                .username(sysUserPo.getUserName())
                .nickname(sysUserPo.getNickName())
                .email(sysUserPo.getEmail())
                .roleCodes(rolePos.stream().map(SysRoleDo::getRoleCode).map(String::valueOf).toList())
                .permissions(rolePos.stream().map(SysRoleDo::getRoleCode).map(String::valueOf).map(roleId -> "ROLE_" + roleId).toList())
                .build();
    }

    AuthLoginBo authLoginDtoToBo(AuthLoginDto authLoginDto);
}


