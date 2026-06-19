package com.sharad.oauthdemowithjwt.user;

import com.sharad.oauthdemowithjwt.utils.TimeStamp;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
@SuperBuilder
public abstract class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String externalId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String username;

    private List<String> roles;

    @Column(unique = true)
    private String refreshToken;

    private Instant refreshTokenExpiry;




}
