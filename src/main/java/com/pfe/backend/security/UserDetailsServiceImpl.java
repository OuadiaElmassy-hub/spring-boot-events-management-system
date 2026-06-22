package com.pfe.backend.security;

import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByUsernameWithRoles(username)//findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : "+ username));

        if (user.getStatutCompte() == StatutCompte.INACTIF || !user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Compte en attente de validation par l'admin");
        }

        return new MyUserDetails(user);
    }

}
