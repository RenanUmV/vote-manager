package com.manager.votemanager.models.entity;

import com.manager.votemanager.models.entity.audit.DateAudit;
import com.manager.votemanager.models.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Builder
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMAIL", "CPF"})})
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateAudit implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    @Size(min = 5, max = 50)
    private String name;

    @NotNull
    @Column(name = "CPF")
    private String cpf;

    @NotNull
    @Email
    @Column(name = "EMAIL")
    @Size(min = 10, max = 30)
    private String email;

    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 6)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 20)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return  Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
