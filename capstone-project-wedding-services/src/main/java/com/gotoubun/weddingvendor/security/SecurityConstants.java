package com.gotoubun.weddingvendor.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/account/**";
    public static final String SIGN_UP_ADMIN_URLS = "/admin/**";
    public static final String SIGN_UP_CUSTOMER_URLS = "/customer/**";
    public static final String SIGN_UP_VENDOR_URLS = "/vendor/**";
    public static final String SINGLE_SERVICE_URLS = "/single-service/**";
    public static final String SERVICE_PACK_URLS = "/service-pack/**";
    public static final String SIGN_UP_KOL_URLS = "/kol/**";
    public static final String SINGLE_CATEGORY_URLS = "/single-category/**";
    public static final String PACKAGE_CATEGORY_URLS = "/package-category/**";
    public static final String BLOG_URLS = "/blog/**";
    public static final String BUDGET_URL = "/budget/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 30000_000; //3000 seconds
}