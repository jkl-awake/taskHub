package com.example.taskhub.common.constant;

public final class SecurityConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_CLAIM_USER_ID = "uid";
    public static final String JWT_CLAIM_USERNAME = "uname";
    public static final String JWT_CLAIM_SESSION_ID = "sid";
    public static final String DEFAULT_ROLE_CODE = "c1108df7-2680-11f1-be1f-0242ac130002";

    private SecurityConstants() {
    }
}
