package com.example.taskhub.converter.auth;

import com.example.taskhub.model.bo.AuthLoginBo;
import com.example.taskhub.model.bo.AuthRegisterBo;
import com.example.taskhub.model.dto.AuthLoginDto;
import com.example.taskhub.model.dto.AuthRegisterDto;
import com.example.taskhub.model.po.SysRolePo;
import com.example.taskhub.model.po.SysUserPo;
import com.example.taskhub.model.vo.CurrentUserVo;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AuthConverter {

    AuthRegisterBo authRegisterDtoToBo(AuthRegisterDto authRegisterDto);

    default SysUserPo authRegisterBoToPo(AuthRegisterBo authRegisterBo,PasswordEncoder passwordEncoder,LocalDateTime now){
        return SysUserPo.builder()
                .username(authRegisterBo.getUsername())
                .password(passwordEncoder.encode(authRegisterBo.getPassword()))
                .nickname(StringUtils.hasText(authRegisterBo.getNickname()) ? authRegisterBo.getNickname() : authRegisterBo.getUsername())
                .email(authRegisterBo.getEmail())
                .status(1)
                .createdAt(now)
                .updatedAt(now)
                .deleted(false)
                .build()
                ;
    }

    default CurrentUserVo sysUserPoToCurrentUserVo(SysUserPo sysUserPo, List<SysRolePo> rolePos){
        return CurrentUserVo.builder()
                .id(sysUserPo.getId())
                .username(sysUserPo.getUsername())
                .nickname(sysUserPo.getNickname())
                .email(sysUserPo.getEmail())
                .roleCodes(rolePos.stream().map(SysRolePo::getRoleCode).map(String::valueOf).toList())
                .permissions(rolePos.stream().map(SysRolePo::getRoleCode).map(String::valueOf).map(roleId -> "ROLE_" + roleId).toList())
                .build();
    }

    AuthLoginBo authLoginDtoToBo(AuthLoginDto authLoginDto);
}

