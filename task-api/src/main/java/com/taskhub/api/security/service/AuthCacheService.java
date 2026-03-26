package com.taskhub.api.security.service;

import com.taskhub.api.common.constant.RedisKeyConstants;
import com.taskhub.api.security.model.AuthUserBo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthCacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public void cacheLogin(AuthUserBo authUserBo, long expireSeconds) {
        String userKey = RedisKeyConstants.LOGIN_USER_PREFIX + authUserBo.getUserId();
        String permissionKey = RedisKeyConstants.LOGIN_PERMISSION_PREFIX + authUserBo.getUserId();
        String sessionKey = RedisKeyConstants.LOGIN_SESSION_PREFIX + authUserBo.getUserId();
        Duration ttl = Duration.ofSeconds(expireSeconds);
        try {
            stringRedisTemplate.opsForValue().set(userKey, objectMapper.writeValueAsString(authUserBo), ttl);
            stringRedisTemplate.opsForValue().set(permissionKey, objectMapper.writeValueAsString(authUserBo.getPermissions()), ttl);
            stringRedisTemplate.opsForValue().set(sessionKey, authUserBo.getSessionId(), ttl);
        } catch (Exception exception) {
            throw new IllegalStateException("Cache login state failed", exception);
        }
    }

    public AuthUserBo getLoginUser(Long userId) {
        String value = stringRedisTemplate.opsForValue().get(RedisKeyConstants.LOGIN_USER_PREFIX + userId);
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return objectMapper.readValue(value, AuthUserBo.class);
        } catch (Exception exception) {
            return null;
        }
    }

    public List<String> getPermissions(Long userId) {
        String value = stringRedisTemplate.opsForValue().get(RedisKeyConstants.LOGIN_PERMISSION_PREFIX + userId);
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {
            });
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    public boolean isSessionValid(Long userId, String sessionId) {
        String cachedSessionId = stringRedisTemplate.opsForValue().get(RedisKeyConstants.LOGIN_SESSION_PREFIX + userId);
        return StringUtils.hasText(sessionId) && sessionId.equals(cachedSessionId);
    }
}

