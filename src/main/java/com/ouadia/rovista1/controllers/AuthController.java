package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.RegisterClientRequest;
import com.ouadia.rovista1.dtos.RegisterOrganisateurRequest;
import com.ouadia.rovista1.dtos.auth.AuthResponse;
import com.ouadia.rovista1.dtos.auth.LoginRequest;
import com.ouadia.rovista1.dtos.auth.RegisterRequest;
import com.ouadia.rovista1.dtos.utilisateur.UserDTO;
import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.RoleNotFoundException;
import com.ouadia.rovista1.security.JwtService;
import com.ouadia.rovista1.security.MyUserDetails;
import com.ouadia.rovista1.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
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
