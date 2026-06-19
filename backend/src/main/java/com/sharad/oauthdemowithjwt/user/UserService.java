package com.sharad.oauthdemowithjwt.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final DBUserRepository dbUserRepository;
    private final OAuthUserRepository oAuthUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(DBUserRepository dbUserRepository, OAuthUserRepository oAuthUserRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.dbUserRepository = dbUserRepository;
        this.oAuthUserRepository = oAuthUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByExternalId(String externalId) {

        Optional<User> user = dbUserRepository.findByExternalId(externalId)
                .map(u -> (User) u)
                .or(() -> oAuthUserRepository.findByExternalId(externalId)
                        .map(u -> (User) u));

        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByRefreshToken(String refreshToken) {

        Optional<User> user = dbUserRepository.findByRefreshToken(refreshToken)
                .map(u -> (User) u)
                .or(() -> oAuthUserRepository.findByRefreshToken(refreshToken)
                        .map(u -> (User) u));

        return user.orElseThrow(() -> new IllegalArgumentException("Refresh token invalid"));
    }

    public void save(User user) {
        if (user instanceof DBUser) {
            dbUserRepository.save((DBUser) user);
        } else if (user instanceof OAuthUser) {
            oAuthUserRepository.save((OAuthUser) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }

    public User findByProviderAndProviderUserId(String provider, String providerUserId) {
        return oAuthUserRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .map(u -> (User) u)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User createUser(RegisterUserRequest newUser) {
        User isAlreadyUser = dbUserRepository.findByUsername(newUser.getUsername())
                .orElse(null);
        if (isAlreadyUser == null) {
            DBUser dbUser = DBUser.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .username(newUser.getUsername())
                    .authType(AuthType.DB)
                    .externalId(UUID.randomUUID().toString())
                    .roles(List.of("User"))
                    .build();

            dbUserRepository.save(dbUser);

            return dbUser;
        }

        throw new IllegalArgumentException("User already exists with that username. Please enter another username");
    }

    public User findByUsername(String username) {
        return dbUserRepository.findByUsername(username)
                .map(u -> (User) u)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
