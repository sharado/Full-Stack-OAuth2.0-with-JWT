package com.sharad.oauthdemowithjwt.security;

public interface OAuthUserInfo {
    String getId();
    String getName();
    String getEmail();
    String getAvatarUrl();
}
