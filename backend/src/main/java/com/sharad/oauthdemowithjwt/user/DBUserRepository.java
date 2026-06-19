package com.sharad.oauthdemowithjwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBUserRepository extends JpaRepository<DBUser,Long> {

    Optional<DBUser> findByExternalId(String externalId);

    Optional<DBUser> findByRefreshToken(String token);

    Optional<DBUser> findByUsername(String username);
}
