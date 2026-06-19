package com.sharad.oauthdemowithjwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface OAuthUserRepository extends JpaRepository<OAuthUser,Long> {

    Optional<OAuthUser> findByExternalId(String externalId);

    Optional<OAuthUser> findByRefreshToken(String token);

    Optional<OAuthUser> findByProviderAndProviderUserId(String provider, String id);
}
