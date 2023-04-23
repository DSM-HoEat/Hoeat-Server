package com.example.hoeatserver.domain.user.domain;

import com.example.hoeatserver.domain.user.enums.ProviderType;
import com.example.hoeatserver.domain.user.enums.Role;
import com.example.hoeatserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "tbl_user")
public class User extends BaseEntity {

    private String email;

    @Length(min = 2, max = 15)
    private String nickName;

    @Length(max = 68)
    private String password;

    @Length(min = 1, max = 30)
    private String introduction;

    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;

    @Builder
    public User(String email, String nickName, String password, String profile, ProviderType providerType) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.profile = profile;
        this.role = Role.ROLE_USER;
        this.providerType = providerType;
    }
}
