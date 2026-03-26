package com.taskhub.api.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.taskhub.api.common.constant.SecurityConstants;
import com.taskhub.api.common.exception.BusinessException;
import com.taskhub.api.converter.auth.AuthConverter;
import com.taskhub.api.model.bo.AuthLoginBo;
import com.taskhub.api.model.bo.AuthRegisterBo;
import com.taskhub.api.model.vo.AuthLoginVo;
import com.taskhub.api.model.vo.CurrentUserVo;
import com.taskhub.api.security.config.SecurityProperties;
import com.taskhub.api.security.model.AuthUserBo;
import com.taskhub.api.security.model.SecurityUser;
import com.taskhub.api.security.service.AuthCacheService;
import com.taskhub.api.security.service.AuthService;
import com.taskhub.api.security.service.JwtService;
import com.taskhub.api.service.role.RoleService;
import com.taskhub.api.service.role.UserRoleService;
import com.taskhub.api.service.user.UserService;
import com.taskhub.auto.entity.SysRoleDo;
import com.taskhub.auto.entity.SysUserDo;
import com.taskhub.auto.entity.SysUserRoleDo;
import com.taskhub.auto.mapper.SysRoleMapper;
import com.taskhub.auto.mapper.SysUserMapper;
import com.taskhub.auto.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthCacheService authCacheService;
    private final SecurityProperties securityProperties;
    private final AuthConverter authConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CurrentUserVo register(AuthRegisterBo authRegisterBo) {
        validateRegister(authRegisterBo);
        LocalDateTime now = LocalDateTime.now();

        SysUserDo sysUserDo = userInit(authRegisterBo,passwordEncoder,now);
        userService.save(sysUserDo);

        SysRoleDo defaultRole = getOrCreateDefaultRole();
        userRoleService.save(SysUserRoleDo.builder()
                .userId(sysUserDo.getId())
                .roleId(defaultRole.getId())
                .createdAt(now)
                .build());

        return authConverter.sysUserPoToCurrentUserVo(sysUserDo, List.of(defaultRole));
    }

    @Override
    public AuthLoginVo login(AuthLoginBo authLoginBo) {
        SysUserDo sysUserDo = userService.getOne(
                Wrappers.<SysUserDo>lambdaQuery()
                        .eq(SysUserDo::getUserName, authLoginBo.getUsername())
        );

        if (sysUserDo == null || !passwordEncoder.matches(authLoginBo.getPassword(), sysUserDo.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (sysUserDo.getStatus() != 1) {
            throw new BusinessException(403, "用户已被禁用");
        }

        List<String> roleCodes = loadRoleCodesByUserId(sysUserDo.getId());
        List<String> permissions = roleCodes.stream().map(this::toRoleAuthority).toList();
        String sessionId = UUID.randomUUID().toString().replace("-", "");
        AuthUserBo authUserBo = AuthUserBo.defaultAuthUserBo(sysUserDo, sessionId, roleCodes, permissions);
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
            throw new BusinessException(401, "未登录或登录状态已失效");
        }

        AuthUserBo authUserBo = authCacheService.getLoginUser(securityUser.getUserId());
        if (authUserBo == null) {
            throw new BusinessException(401, "登录状态已失效，请重新登录");
        }

        return toCurrentUserVo(authUserBo);
    }

    private void validateRegister(AuthRegisterBo authRegisterBo) {
        long usernameCount = userService.count(
                Wrappers.<SysUserDo>lambdaQuery().eq(SysUserDo::getUserName, authRegisterBo.getUsername())
        );
        if (usernameCount > 0) {
            throw new BusinessException(400, "用户名已存在");
        }

        if (StringUtils.hasText(authRegisterBo.getEmail())) {
            long emailCount = userService.count(
                    Wrappers.<SysUserDo>lambdaQuery().eq(SysUserDo::getEmail, authRegisterBo.getEmail())
            );
            if (emailCount > 0) {
                throw new BusinessException(400, "邮箱已被使用");
            }
        }
    }

    private SysRoleDo getOrCreateDefaultRole() {
        SysRoleDo defaultRole = roleService.getOne(
                Wrappers.<SysRoleDo>lambdaQuery().eq(SysRoleDo::getRoleCode, SecurityConstants.DEFAULT_ROLE_CODE)
        );
        if (defaultRole != null) {
            return defaultRole;
        }

        LocalDateTime now = LocalDateTime.now();
        defaultRole = SysRoleDo.builder()
                .roleCode(SecurityConstants.DEFAULT_ROLE_CODE)
                .roleName("默认角色")
                .remark("系统默认角色")
                .createdAt(now)
                .updatedAt(now)
                .deleted(0)
                .build();
        roleService.save(defaultRole);
        return defaultRole;
    }

    private List<String> loadRoleCodesByUserId(Long userId) {
        List<SysUserRoleDo> userRoles = userRoleService.list(
                Wrappers.<SysUserRoleDo>lambdaQuery().eq(SysUserRoleDo::getUserId, userId)
        );
        if (userRoles == null || userRoles.isEmpty()) {
            return List.of();
        }

        List<Long> roleIds = userRoles.stream().map(SysUserRoleDo::getRoleId).toList();
        return roleService.list(
                Wrappers.<SysRoleDo>lambdaQuery().in(SysRoleDo::getId, roleIds)
        ).stream()
                .map(SysRoleDo::getRoleCode)
                .filter(StringUtils::hasText)
                .toList();
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

    private SysUserDo userInit(AuthRegisterBo authRegisterBo,PasswordEncoder passwordEncoder,LocalDateTime now){
        return SysUserDo.builder()
                .userName(authRegisterBo.getUsername())
                .password(passwordEncoder.encode(authRegisterBo.getPassword()))
                .nickName(StringUtils.hasText(authRegisterBo.getNickname()) ? authRegisterBo.getNickname() : authRegisterBo.getUsername())
                .email(authRegisterBo.getEmail())
                .status(1)
                .createdAt(now)
                .updatedAt(now)
                .deleted(0)
                .build();
    }
}
