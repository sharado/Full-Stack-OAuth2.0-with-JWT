package com.sharad.oauthdemowithjwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class OAuthDemoWithJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthDemoWithJwtApplication.class, args);
    }



}
