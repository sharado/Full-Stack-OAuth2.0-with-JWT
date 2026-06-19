package com.sharad.oauthdemowithjwt.security;

import com.sharad.oauthdemowithjwt.user.AuthType;
import com.sharad.oauthdemowithjwt.user.OAuthUser;
import com.sharad.oauthdemowithjwt.user.OAuthUserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthUserRepository oAuthUserRepository;

    public CustomOAuth2UserService(OAuthUserRepository oAuthUserRepository) {
        this.oAuthUserRepository = oAuthUserRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthUserInfo oAuthUserInfo = OAuthUserInfoFactory.create(provider, attributes);

        OAuthUser user = oAuthUserRepository.findByProviderAndProviderUserId(provider, oAuthUserInfo.getId())
                .map(existing -> update(existing, oAuthUserInfo))
                .orElseGet(() -> create(provider, oAuthUserInfo));

        oAuthUserRepository.save(user);

        return oAuth2User;
    }


    private OAuthUser create(String provider, OAuthUserInfo oAuthUserInfo) {
        return OAuthUser.builder()
                .provider(provider)
                .providerUserId(oAuthUserInfo.getId())
                .externalId(UUID.randomUUID().toString())
                .roles(List.of("USER"))
                .authType(AuthType.OAUTH)
                .email(oAuthUserInfo.getEmail())
                .username(oAuthUserInfo.getName())
                .avatarUrl(oAuthUserInfo.getAvatarUrl())
                .build();

    }

    private OAuthUser update(OAuthUser existing, OAuthUserInfo oAuthUserInfo) {
        existing.setAvatarUrl(oAuthUserInfo.getAvatarUrl());
        existing.setEmail(oAuthUserInfo.getEmail());
        existing.setUsername(oAuthUserInfo.getName());
        return existing;
    }
}
