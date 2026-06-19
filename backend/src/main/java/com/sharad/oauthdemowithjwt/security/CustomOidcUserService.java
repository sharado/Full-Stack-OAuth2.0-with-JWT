package com.sharad.oauthdemowithjwt.security;


import com.sharad.oauthdemowithjwt.user.AuthType;
import com.sharad.oauthdemowithjwt.user.OAuthUser;
import com.sharad.oauthdemowithjwt.user.OAuthUserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final OAuthUserRepository userRepository;

    public CustomOidcUserService(OAuthUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oidcUser.getAttributes();
        OAuthUserInfo oAuthUserInfo = OAuthUserInfoFactory.create(provider, attributes);

        OAuthUser user = userRepository.findByProviderAndProviderUserId(provider, oAuthUserInfo.getId())
                .map(existing -> update(existing, oAuthUserInfo))
                .orElseGet(() -> create(provider, oAuthUserInfo));

        userRepository.save(user);

        return oidcUser;
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
