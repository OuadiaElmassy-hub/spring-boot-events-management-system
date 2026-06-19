package com.pfe.backend.services.implementations;

import com.pfe.backend.entities.Role;
import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.services.interfaces.accountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final accountService accountservice;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = accountservice.loedUserByUsername(username);
        if (user==null) throw new UsernameNotFoundException(String.format("User %s not found",username));
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getMotDePasse())
                .roles(user.getRoles().stream().map(Role::getRoleName)
                        .toArray(String[]::new)).build();
        return userDetails;
    }
}
