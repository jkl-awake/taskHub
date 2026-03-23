package com.example.taskhub.security.service;

import com.example.taskhub.common.constant.SecurityConstants;
import com.example.taskhub.security.config.SecurityProperties;
import com.example.taskhub.security.model.AuthUserBo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityProperties securityProperties;

    public String generateToken(AuthUserBo authUserBo) {
        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(securityProperties.getAccessTokenExpireSeconds());
        return Jwts.builder()
                .issuer(securityProperties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expireAt))
                .claim(SecurityConstants.JWT_CLAIM_USER_ID, authUserBo.getUserId())
                .claim(SecurityConstants.JWT_CLAIM_USERNAME, authUserBo.getUsername())
                .claim(SecurityConstants.JWT_CLAIM_SESSION_ID, authUserBo.getSessionId())
                .signWith(getSecretKey())
                .compact();
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            return null;
        }
    }

    public Long getUserId(Claims claims) {
        Object userId = claims.get(SecurityConstants.JWT_CLAIM_USER_ID);
        return userId == null ? null : Long.valueOf(String.valueOf(userId));
    }

    public String getSessionId(Claims claims) {
        Object sessionId = claims.get(SecurityConstants.JWT_CLAIM_SESSION_ID);
        return sessionId == null ? null : String.valueOf(sessionId);
    }

    private SecretKey getSecretKey() {
        byte[] bytes;
        try {
            bytes = Decoders.BASE64.decode(securityProperties.getSecret());
        } catch (Exception exception) {
            bytes = securityProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(bytes);
    }
}
