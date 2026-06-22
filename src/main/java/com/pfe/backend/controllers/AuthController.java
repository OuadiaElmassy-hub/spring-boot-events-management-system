package com.pfe.backend.controllers;

import com.pfe.backend.dtos.RegisterClientRequest;
import com.pfe.backend.dtos.RegisterOrganisateurRequest;
import com.pfe.backend.dtos.auth.AuthResponse;
import com.pfe.backend.dtos.auth.LoginRequest;
import com.pfe.backend.dtos.utilisateur.UserDTO;
import com.pfe.backend.entities.Role;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.exceptions.RoleNotFoundException;
import com.pfe.backend.security.MyUserDetails;
import com.pfe.backend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;



    @PostMapping("/register/organisateur")
    public ResponseEntity<String> registerOrganisateur(
            @RequestBody @Valid RegisterOrganisateurRequest request) throws BusinessException, RoleNotFoundException {
        return ResponseEntity.ok(authService.registerOrganisateur(request));
    }
    @PostMapping("/register/client")
    public ResponseEntity<AuthResponse> registerClient(
            @RequestBody @Valid RegisterClientRequest request) throws BusinessException, RoleNotFoundException {
        return ResponseEntity.ok(authService.registerClient(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserDTO getMe(@AuthenticationPrincipal MyUserDetails user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();
        return UserDTO.builder()
                .nom(user.getNom())
                .id(user.getId())
                .roles(roles)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody String request) {
        String refreshToken = authService.refresh(request);

        if(refreshToken == null){
            return ResponseEntity
                    .status(401)
                    .build();
        }

        return ResponseEntity.ok(refreshToken);
    }

    /* Logout sécurisé
    Créer une table :

    @Entity
    public class RevokedToken {
        @Id
        @GeneratedValue
        private Long id;
        private String token;
        private LocalDateTime revokedAt;
    }

    Endpoint :
    @PostMapping("/logout")
    public void logout( HttpServletRequest request){
        String token = extractToken(request);

        revokedTokenRepository.save( new RevokedToken(
                        token,
                        LocalDateTime.now()));
    }

    Puis dans JwtFilter :

            if(revokedTokenRepository
            .existsByToken(token)){
        filterChain.doFilter(request, response);
        return;
    }*/

}
