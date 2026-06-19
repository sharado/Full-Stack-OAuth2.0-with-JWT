package com.sharad.oauthdemowithjwt.security;

import com.sharad.oauthdemowithjwt.user.AuthType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtService {
    private final SecretKey SECRET;
    private final long EXPIRY;

    public JwtService(
            @Value("${jwt.access.token.secret}") String SECRET,
            @Value("${jwt.access.token.expiry}") long EXPIRY
    ) {
        this.SECRET = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        this.EXPIRY = EXPIRY;
    }

    public String generateAccessToken(AuthType authType, String externalId, List<String> roles) {
        return Jwts.builder()
                .setSubject(authType + ":" + externalId)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY))
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parse(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
