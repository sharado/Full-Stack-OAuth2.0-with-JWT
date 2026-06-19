package com.sharad.oauthdemowithjwt.security;

import com.sharad.oauthdemowithjwt.user.User;
import com.sharad.oauthdemowithjwt.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserService userService;

    private final long refreshTokenExpiry;

    public RefreshTokenService(UserService userService,
                               @Value("${jwt.refresh.token.expiry}") long refreshTokenExpiry) {
        this.userService = userService;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public String create(String externalId) {
        User user = userService.findByExternalId(externalId);
        String token = UUID.randomUUID().toString();
        user.setRefreshToken(token);
        user.setRefreshTokenExpiry(Instant.now().plusMillis(refreshTokenExpiry));
        userService.save(user);
        return token;
    }

    public String validate(String refreshToken) {
        User user = userService.findByRefreshToken(refreshToken);
        if (user.getRefreshTokenExpiry().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        return user.getUsername();
    }

    public void revoke(String refreshToken) {
        User user = userService.findByRefreshToken(refreshToken);
        user.setRefreshToken(null);
        user.setRefreshTokenExpiry(null);
        userService.save(user);
    }

}
