package com.example.taskhub.common.constant;

public final class RedisKeyConstants {

    private static final String PREFIX = "taskhub:auth:";

    public static final String LOGIN_USER_PREFIX = PREFIX + "login:user:";
    public static final String LOGIN_PERMISSION_PREFIX = PREFIX + "login:permission:";
    public static final String LOGIN_SESSION_PREFIX = PREFIX + "login:session:";

    private RedisKeyConstants() {
    }
}
