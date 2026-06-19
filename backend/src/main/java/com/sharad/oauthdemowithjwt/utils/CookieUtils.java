package com.sharad.oauthdemowithjwt.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.util.Arrays;
import java.util.Optional;

public final class CookieUtils {

    private CookieUtils() {}

    /* =======================
       READ COOKIE (request)
       ======================= */

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    /* =======================
       ADD COOKIE (response)
       ======================= */

    public static void addCookie(HttpServletResponse response,
                                 String name,
                                 String value,
                                 int maxAgeSeconds,
                                 boolean httpOnly) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(httpOnly)
                .secure(true)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /* =======================
       DELETE COOKIE (response)
       ======================= */

    public static void deleteCookie(HttpServletResponse response,
                                    String name) {

        ResponseCookie cookie = ResponseCookie.from(name, "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
