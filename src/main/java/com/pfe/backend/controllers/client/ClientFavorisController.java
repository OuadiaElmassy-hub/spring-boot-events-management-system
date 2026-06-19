package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.favorie.FavorieResponseDto;
import com.pfe.backend.dtos.favorie.HistoriqueFavorieDto;
import com.pfe.backend.exceptions.BusinessException;
import com.pfe.backend.exceptions.ClientNotFoundException;
import com.pfe.backend.exceptions.EventNotFoundException;
import com.pfe.backend.exceptions.FavorieNotFoundException;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.interfaces.IFavorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/favoris")
@RequiredArgsConstructor
public class ClientFavorisController {

    private final SecurityUtils securityUtils;
    private final IFavorieService favoriteService;

    // GET /api/client/favoris?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<HistoriqueFavorieDto>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(favoriteService.getFavories(userId, page, size));
    }

    // POST /api/client/favoris/{eventId}
    @PostMapping("/{eventId}")
    public ResponseEntity<FavorieResponseDto> addFavorie(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetails userDetails) throws BusinessException, ClientNotFoundException, EventNotFoundException, FavorieNotFoundException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoriteService.addFavorie(securityUtils.getCurrentUserId(), eventId));
    }

    // DELETE /api/client/favories/{eventId}
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        favoriteService.removeFavorie(securityUtils.getCurrentUserId(), eventId);
        return ResponseEntity.noContent().build(); // 204
    }

}