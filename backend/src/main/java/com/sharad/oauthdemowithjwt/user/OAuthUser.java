package com.sharad.oauthdemowithjwt.user;

import com.sharad.oauthdemowithjwt.utils.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "oauth_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "providerUserId"})
        }
)
@Data
@NoArgsConstructor
@SuperBuilder
public class OAuthUser extends User {

    private String email;
    private String provider;
    private String providerUserId;
    private String avatarUrl;
}
