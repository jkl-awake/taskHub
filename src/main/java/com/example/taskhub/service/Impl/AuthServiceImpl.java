package com.example.taskhub.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.taskhub.common.constant.SecurityConstants;
import com.example.taskhub.common.exception.BusinessException;
import com.example.taskhub.converter.auth.AuthConverter;
import com.example.taskhub.model.bo.AuthLoginBo;
import com.example.taskhub.model.bo.AuthRegisterBo;
import com.example.taskhub.model.dto.AuthLoginDto;
import com.example.taskhub.model.po.SysRolePo;
import com.example.taskhub.model.po.SysUserPo;
import com.example.taskhub.model.po.SysUserRolePo;
import com.example.taskhub.model.vo.AuthLoginVo;
import com.example.taskhub.model.vo.CurrentUserVo;
import com.example.taskhub.repository.SysRoleMapperExt;
import com.example.taskhub.repository.SysUserMapperExt;
import com.example.taskhub.repository.SysUserRoleMapperExt;
import com.example.taskhub.security.config.SecurityProperties;
import com.example.taskhub.security.model.AuthUserBo;
import com.example.taskhub.security.model.SecurityUser;
import com.example.taskhub.security.service.AuthCacheService;
import com.example.taskhub.security.service.AuthService;
import com.example.taskhub.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapperExt sysUserMapper;
    private final SysRoleMapperExt sysRoleMapper;
    private final SysUserRoleMapperExt sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthCacheService authCacheService;
    private final SecurityProperties securityProperties;
    private final AuthConverter authConverter;

    /*
    * 注册
    * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CurrentUserVo register(AuthRegisterBo authRegisterBo) {
        validateRegister(authRegisterBo);
        LocalDateTime now = LocalDateTime.now();

        //注册用户
        SysUserPo sysUserPo = authConverter.authRegisterBoToPo(authRegisterBo,passwordEncoder,now);
        sysUserMapper.insert(sysUserPo);

        //绑定角色
        SysRolePo defaultRole = getOrCreateDefaultRole();
        sysUserRoleMapper.insert(SysUserRolePo.builder()
                .userId(sysUserPo.getId())
                .roleId(defaultRole.getId())
                .createdAt(now)
                .build());

        return authConverter.sysUserPoToCurrentUserVo(sysUserPo,List.of(defaultRole));
    }

    @Override
    public AuthLoginVo login(AuthLoginBo authLoginBo) {
        SysUserPo sysUserPo = sysUserMapper.selectOne(
                Wrappers.<SysUserPo>lambdaQuery()
                        .eq(SysUserPo::getUsername, authLoginBo.getUsername())
        );

        if (sysUserPo == null || !passwordEncoder.matches(authLoginBo.getPassword(), sysUserPo.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (sysUserPo.getStatus() == null || sysUserPo.getStatus() != 1) {
            throw new BusinessException(403, "账号已被禁用");
        }

        List<String> roleCodes = sysUserRoleMapper.listRoleCodeByUserId(sysUserPo.getId());
        List<String> permissions = roleCodes.stream().map(this::toRoleAuthority).toList();
        String sessionId = UUID.randomUUID().toString().replace("-", "");
        AuthUserBo authUserBo = AuthUserBo.defaultAuthUserBo(sysUserPo, sessionId, roleCodes, permissions);
        authCacheService.cacheLogin(authUserBo, securityProperties.getAccessTokenExpireSeconds());

        return AuthLoginVo.builder()
                .token(jwtService.generateToken(authUserBo))
                .tokenType("Bearer")
                .expiresIn(securityProperties.getAccessTokenExpireSeconds())
                .userInfo(toCurrentUserVo(authUserBo))
                .build();
    }

    @Override
    public CurrentUserVo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser securityUser)) {
            throw new BusinessException(401, "未登录");
        }
        AuthUserBo authUserBo = authCacheService.getLoginUser(securityUser.getUserId());
        if (authUserBo == null) {
            throw new BusinessException(401, "登录状态已失效");
        }
        return toCurrentUserVo(authUserBo);
    }

    private void validateRegister(AuthRegisterBo authRegisterBo) {
        Long usernameCount = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUserPo>()
                .eq(SysUserPo::getUsername, authRegisterBo.getUsername()));
        if (usernameCount != null && usernameCount > 0) {
            throw new BusinessException("用户名已存在");
        }
        if (StringUtils.hasText(authRegisterBo.getEmail())) {
            Long emailCount = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUserPo>()
                    .eq(SysUserPo::getEmail, authRegisterBo.getEmail()));
            if (emailCount != null && emailCount > 0) {
                throw new BusinessException("邮箱已存在");
            }
        }
    }

    private SysRolePo getOrCreateDefaultRole() {
        SysRolePo defaultRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRolePo>()
                .eq(SysRolePo::getRoleCode, SecurityConstants.DEFAULT_ROLE_CODE)
                .last("limit 1"));
        if (defaultRole != null) {
            return defaultRole;
        }

        defaultRole = SysRolePo.defaultRole();
        sysRoleMapper.insert(defaultRole);
        return defaultRole;
    }

    private CurrentUserVo toCurrentUserVo(AuthUserBo authUserBo) {
        return CurrentUserVo.builder()
                .id(authUserBo.getUserId())
                .username(authUserBo.getUsername())
                .nickname(authUserBo.getNickname())
                .email(authUserBo.getEmail())
                .roleCodes(authUserBo.getRoleCodes())
                .permissions(authUserBo.getPermissions())
                .build();
    }

    private String toRoleAuthority(String roleCode) {
        return "ROLE_" + roleCode;
    }
}
