package com.sharad.oauthdemowithjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
public class Config {

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }
}
