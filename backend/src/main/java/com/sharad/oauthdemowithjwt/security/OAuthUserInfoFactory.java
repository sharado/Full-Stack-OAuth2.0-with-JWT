package com.sharad.oauthdemowithjwt.security;


import java.util.Map;

public class OAuthUserInfoFactory {
    public static OAuthUserInfo create(String provider, Map<String,Object> attributes){
        OAuthUserInfo oAuthUserInfo = switch (provider){
            case "google" -> new GoogleUserInfo(attributes);
            case "github" -> new GithubUserInfo(attributes);
            default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
        return oAuthUserInfo;
    }
}
