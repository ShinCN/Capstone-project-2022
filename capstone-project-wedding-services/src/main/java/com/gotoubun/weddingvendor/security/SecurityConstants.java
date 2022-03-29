package com.gotoubun.weddingvendor.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/account/**";
    public static final String SIGN_UP_ADMIN_URLS = "/admin/**";
    public static final String SIGN_UP_CUSTOMER_URLS = "/customer/**";
    public static final String SIGN_UP_VENDOR_URLS = "/vendor/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; //30 seconds
}
