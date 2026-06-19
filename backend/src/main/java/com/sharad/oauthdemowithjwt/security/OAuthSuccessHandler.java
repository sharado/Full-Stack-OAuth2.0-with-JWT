package com.sharad.oauthdemowithjwt.security;

import com.sharad.oauthdemowithjwt.user.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final RequestCache requestCache;
    private final long refreshTokenExpiry;
    private final AuthService authService;

    public OAuthSuccessHandler(JwtService jwtService, RefreshTokenService refreshTokenService, UserService userService, RequestCache requestCache,
                              @Value("${jwt.refresh.token.expiry}") long refreshTokenExpiry, AuthService authService) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.requestCache = requestCache;
        this.refreshTokenExpiry = refreshTokenExpiry;
        this.authService = authService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        authService.issueTokensAndSetCookies(auth, response);

        String redirectTo = "/";
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("redirect_after_login".equals(c.getName())) {
                    redirectTo = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                    break;
                }
            }
        }

        if (!redirectTo.startsWith("/")) {
            redirectTo = "/";
        }

        authService.deleteRedirectCookies(response);

        response.sendRedirect("http://localhost:5173" + redirectTo);
//        response.sendRedirect(redirectTo);
    }
}
