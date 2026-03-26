package com.taskhub.api.security.filter;

import com.taskhub.api.common.constant.SecurityConstants;
import com.taskhub.api.security.model.AuthUserBo;
import com.taskhub.api.security.model.SecurityUser;
import com.taskhub.api.security.service.AuthCacheService;
import com.taskhub.api.security.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthCacheService authCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(SecurityConstants.TOKEN_PREFIX.length());
        Claims claims = jwtService.parseClaims(token);
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.getUserId(claims);
        String sessionId = jwtService.getSessionId(claims);
        if (userId == null || !authCacheService.isSessionValid(userId, sessionId)) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthUserBo authUserBo = authCacheService.getLoginUser(userId);
        if (authUserBo == null) {
            filterChain.doFilter(request, response);
            return;
        }

        List<SimpleGrantedAuthority> authorities = authCacheService.getPermissions(userId).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        SecurityUser securityUser = SecurityUser.builder()
                .userId(authUserBo.getUserId())
                .username(authUserBo.getUsername())
                .nickname(authUserBo.getNickname())
                .roleCodes(authUserBo.getRoleCodes())
                .permissions(authUserBo.getPermissions())
                .build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

