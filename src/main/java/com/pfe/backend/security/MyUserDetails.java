package com.pfe.backend.security;

import com.pfe.backend.entities.Role;
import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.entities.enums.StatutCompte;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class MyUserDetails implements UserDetails {

    private final Utilisateur user;

    public MyUserDetails(Utilisateur user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getNom() {
        return user.getNom();
    }

    public String getAdresse() {
        return user.getAdresse();
    }

    public String getAvatare() {
        return user.getAvatar();
    }

    public String getPhone() {
        return user.getPhone();
    }

    public LocalDateTime getCreatedAt() {
        return user.getCreatedAt();
    }

    public StatutCompte getStatutCompte() {
        return user.getStatutCompte();
    }

    public Long getId() {
        return user.getId();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getPrenom() {
        return user.getPrenom();
    }

    public List<Role> getRoles() {
        return user.getRoles();
    }

}
