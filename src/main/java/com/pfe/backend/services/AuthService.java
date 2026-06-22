package com.pfe.backend.services;

import com.pfe.backend.dtos.RegisterClientRequest;
import com.pfe.backend.dtos.RegisterOrganisateurRequest;
import com.pfe.backend.dtos.auth.AuthResponse;
import com.pfe.backend.dtos.auth.LoginRequest;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.Organisateur;
import com.pfe.backend.entities.Role;
import com.pfe.backend.entities.enums.StatutCompte;
import com.pfe.backend.entities.enums.StatutOrganisateur;
import com.pfe.backend.exceptions.RoleNotFoundException;
import com.pfe.backend.repositories.*;
import com.pfe.backend.security.JwtService;
import com.pfe.backend.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepo;
    private final ClientRepository clientRepo;
    private final OrganisateurRepository organisateurRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AdminRepository adminRepository;

    public AuthResponse login(LoginRequest request) {

        // Lance UsernameNotFoundException ou BadCredentialsException si invalide
        // alors pas besoin de : if (authentication.isAuthenticated())

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException ex) {
            // Spring a bloqué car enabled=false
            throw new DisabledException("Compte en attente de validation par l'admin");
        }

        // récupère direct depuis Authentication authenticate() te retourne déjà
        // un Authentication avec UserDetails dedans.
        // Pas besoin de recharger :


        // Si on arrive ici = authentifié

        MyUserDetails userDetails = // userDetailsService.loadUserByUsername(request.getUsername());
                // pour ne pas faire une deuxieme requete loadByUsername pour rien :
                (MyUserDetails) authentication.getPrincipal();

        // Bloque si disabled pas encore validé
        if (!userDetails.isEnabled()) {
            throw new DisabledException("Compte en attente de validation par l'admin");
        }

        // pour la verification de hasRole.. spring security doit avoir comme rol ROLE_...
        //!!!!!!!!! verifier !!!!!!!!!!

        // 1. Prépare les claims pour ton React
        List<String> roles = userDetails.getRoles().stream()
                .map(Role::getRoleName)
                .toList();

        // Récupération de ses rôles depuis votre entité (ex: ["CLIENT"])
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        Map<String, Object> extraClaims = Map.of(
                "id", userDetails.getId(),
                "nom", userDetails.getNom(),
                "username", userDetails.getUsername(),
                "email", userDetails.getEmail(),
                "phone", userDetails.getPhone(),
                "prenom", userDetails.getPrenom(),
                "roles", roles // ["ADMIN"] ou ["ORGANISATEUR"] ou ["CLIENT"]
        );

        // 2. Génère le token avec les claims
        String token = jwtService.generateToken(extraClaims, userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse
                .builder()
                .token(token)
                .roles(roles)
                .nom(userDetails.getNom())
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse registerClient(RegisterClientRequest req) throws RoleNotFoundException {
        checkEmailUnique(req.getEmail());

        Client client = new Client();
        client.setUsername(req.getUsername());
        client.setEmail(req.getEmail());
        client.setMotDePasse(passwordEncoder.encode(req.getPassword()));
        client.setNom(req.getNom());
        client.setPrenom(req.getPrenom());
        client.setEnabled(true);
        client.setPhone(req.getPhone());
        client.setAdresse(req.getVille());
        client.setCreatedAt(LocalDateTime.now());
        client.setStatutCompte(StatutCompte.ACTIF);
        Role role = roleRepository.findByRoleName("CLIENT")
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name : CLIENT "));
        client.setRoles(List.of(role));
        client = clientRepo.save(client);

        MyUserDetails userDetails =
                (MyUserDetails) userDetailsService.loadUserByUsername(client.getUsername());

        // 1. Prépare les claims pour ton React

        Map<String, Object> extraClaims = Map.of(
                "id", userDetails.getId(),
                "nom", userDetails.getNom(),
                "username", userDetails.getUsername(),
                "email", userDetails.getEmail(),
                "phone", userDetails.getPhone(),
                "prenom", userDetails.getPrenom(),
                "roles", List.of(role.getRoleName()) // ["CLIENT"]
        );

        // 2. Génère le token avec les claims
        String token = jwtService.generateToken(extraClaims, userDetails);
        return AuthResponse.builder()
                .token(token)
                .roles(List.of(role.getRoleName()))
                .nom(client.getNom())
                .build();
    }

    // juste une soumission de demande :
    public String registerOrganisateur(RegisterOrganisateurRequest req) throws RoleNotFoundException {

        checkEmailUnique(req.getEmail());
        checkUsernameUnique(req.getUsername());

        Organisateur org = new Organisateur();
        org.setUsername(req.getUsername());
        org.setEmail(req.getEmail());
        org.setMotDePasse(passwordEncoder.encode(req.getPassword()));
        org.setNomOrganisation(req.getOrganisationNom());
        org.setNom(req.getNom());
        org.setPrenom(req.getPrenom());
        org.setPhone(req.getPhone());
        org.setAdresse(req.getAdresse());
        org.setSiret(req.getSiret());
        org.setNumRegistre(req.getNumRegister());
        org.setCreatedAt(LocalDateTime.now());
        org.setStatutCompte(StatutCompte.INACTIF);
        org.setStatutOrganisateur(StatutOrganisateur.INACTIF);
        org.setStatutOrganisateur(StatutOrganisateur.SUSPENDU);
        org.setEnabled(false);
        //org.setLogoUrl();
        Role role = roleRepository.findByRoleName("ORGANISATEUR")
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name : ORGANISATEUR "));
        Role role2 = roleRepository.findByRoleName("CLIENT")
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name : CLIENT "));
        org.setRoles(List.of(role,role2));
        org.setVerified(false);
        org = organisateurRepo.save(org);

        return "Demande envoyée. Votre compte sera activé après validation par l'admin.";

        // Pas de generation de token :
//        UserDetails userDetails =
//                userDetailsService.loadUserByUsername(org.getUsername());
//
//        String token = jwtService.generateToken(userDetails);
//        return new AuthResponse(token, List.of(role.getRoleName()), org.getNom());
    }

//    public AuthResponse registerOrganisateur(RegisterOrganisateurRequest req) throws RoleNotFoundException {
//
//        checkEmailUnique(req.getEmail());
//        checkUsernameUnique(req.getUsername());
//
//        Organisateur org = new Organisateur();
//        org.setUsername(req.getUsername());
//        org.setEmail(req.getEmail());
//        org.setMotDePasse(passwordEncoder.encode(req.getPassword()));
//        org.setNom(req.getOrganisationNom());
//        org.setPhone(req.getPhone());
//        org.setAdresse(req.getAdresse());
//        org.setSiret(req.getSiret());
//        org.setNumRegistre(req.getNumRegister());
//        org.setDateValidation(LocalDateTime.now());
//        org.setStatutCompte(StatutCompte.ACTIVE);
//        org.setStatutOrganisateur(StatutOrganisateur.ACTIF);
//        //org.setLogoUrl();
//        Role role = roleRepository.findByRoleName("ORGANISATEUR")
//                .orElseThrow(() -> new RoleNotFoundException("Role not found with name : ORGANISATEUR "));
//        Role role2 = roleRepository.findByRoleName("CLIENT")
//                .orElseThrow(() -> new RoleNotFoundException("Role not found with name : CLIENT "));
//        org.setRoles(List.of(role,role2));
//        org.setVerified(true);
//        org = organisateurRepo.save(org);
//
//        UserDetails userDetails =
//                userDetailsService.loadUserByUsername(org.getUsername());
//
//        String token = jwtService.generateToken(userDetails);
//        return new AuthResponse(token, List.of(role.getRoleName()), org.getNom());
//    }

    public String refresh( String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        UserDetails user =
                userDetailsService
                        .loadUserByUsername(username);

        if(!jwtService.validateToken(refreshToken, user)){
            return null;
        }

        String newAccessToken = jwtService.generateToken(Map.of(), user);

        return newAccessToken;
    }

    private void checkEmailUnique(String email) {
        if (utilisateurRepo.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email déjà utilisé");
        }
    }
    private void checkUsernameUnique(String username) {
        if (utilisateurRepo.existsByUsername(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username déjà utilisé");
        }
    }
}
