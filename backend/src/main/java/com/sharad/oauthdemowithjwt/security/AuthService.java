package com.sharad.oauthdemowithjwt.security;

import com.sharad.oauthdemowithjwt.user.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    public void login(RegisterUserRequest req, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        if (!auth.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        issueTokensAndSetCookies(auth, response);
        deleteRedirectCookies(response);
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie access = ResponseCookie.from("access_token", "")
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refresh = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();


        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refresh.toString());
    }

    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookies[] = request.getCookies();

        Optional<Cookie> refreshToken =
                cookies == null ? Optional.empty() :
                        Arrays.stream(cookies)
                                .filter(c -> "refresh_token".equals(c.getName()))
                                .findFirst();

        if (!refreshToken.isPresent()) {
            // No token → anonymous request
            throw new BadCredentialsException("Unauthorized");
        }

        String token = refreshToken.get().getValue();
        refreshTokenService.validate(token);
        User user = userService.findByRefreshToken(token);
        refreshTokenService.revoke(token);

        // Use externalId as JWT subject
        String accessToken = jwtService.generateAccessToken(user.getAuthType(), user.getExternalId(), user.getRoles());
        String newRefreshToken = refreshTokenService.create(user.getExternalId());

        addCookies(response, accessToken, newRefreshToken);
    }

    public void issueTokensAndSetCookies(Authentication auth, HttpServletResponse response) {
        User user;
        AuthType authType;

        if (auth instanceof UsernamePasswordAuthenticationToken dbAuth) {
            CustomUserDetails userDetails = (CustomUserDetails) dbAuth.getPrincipal();
            user = userDetails.getUser();
            authType = AuthType.DB;
        } else if (auth instanceof OAuth2AuthenticationToken oauthAuth) {
            OAuth2User oauth2User = oauthAuth.getPrincipal();
            String provider = oauthAuth.getAuthorizedClientRegistrationId();
            String providerUserId = oauth2User.getName(); // unique per provider
            user = userService.findByProviderAndProviderUserId(provider, providerUserId);
            authType = AuthType.OAUTH;
        } else {
            throw new IllegalStateException("Unsupported authentication type: " + auth.getClass());
        }

        // Use externalId as JWT subject
        String accessToken = jwtService.generateAccessToken(authType, user.getExternalId(), user.getRoles());
        String refreshToken = refreshTokenService.create(user.getExternalId());

        addCookies(response, accessToken, refreshToken);

    }

    private void addCookies(HttpServletResponse response,
                            String accessToken,
                            String refreshToken) {

        ResponseCookie access = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMinutes(5))
                .build();

        ResponseCookie refresh = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofMinutes(15))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refresh.toString());
    }

    public void deleteRedirectCookies(HttpServletResponse response) {
        ResponseCookie deleteRedirect =
                ResponseCookie.from("redirect_after_login", "")
                        .path("/")
                        .maxAge(0)
                        .sameSite("Lax")
                        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteRedirect.toString());
    }

    public String isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String externalId = auth.getName().split(":")[1];
            return userService.findByExternalId(externalId).getUsername();
        }
        throw new IllegalArgumentException("User not authenticated. Please login to continue");
    }


}
