package com.ouadia.rovista1.controllers.admin;


import com.ouadia.rovista1.dtos.admin.AdminUserDTO;
import com.ouadia.rovista1.dtos.admin.PatchUserStatusRequest;
import com.ouadia.rovista1.exceptions.UtilisateurNotFoundException;
import com.ouadia.rovista1.services.admin.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@EnableMethodSecurity
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService userService;

    // GET /api/admin/users?search=&role=&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<AdminUserDTO>> getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(userService.searchUsers(search, role, page, size));
    }

    // PATCH /api/admin/users/{id}/status
    // Body : { "status": "Inactif" }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> patchStatus(
            @PathVariable Long id,
            @RequestBody @Valid PatchUserStatusRequest req) throws UtilisateurNotFoundException {

        userService.patchStatus(id, req);
        return ResponseEntity.ok().build();
    }

    // Ou avec @PreAuthorize pour plus de granularité
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer l'utilisateur connecté depuis le contexte
//    @GetMapping("/me")
//    public ResponseEntity<UserDTO> me(@AuthenticationPrincipal MyUserDetails principal) {
//        Long id = principal.getId();
//        String email = principal.getEmail();
//        String username = principal.getUsername();
//        return ResponseEntity.ok(userMapper.toDto(principal));
//    }
}