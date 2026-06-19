package com.sharad.oauthdemowithjwt.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "db_users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
)
@Data
@NoArgsConstructor
@SuperBuilder
public class DBUser extends User {

    private String password;
    private String photoUrl;

}
