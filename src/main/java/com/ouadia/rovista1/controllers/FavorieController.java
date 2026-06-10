package com.ouadia.rovista1.controllers;

import com.ouadia.rovista1.dtos.favorie.FavorieResponseDto;
import com.ouadia.rovista1.dtos.favorie.HistoriqueFavorieDto;
import com.ouadia.rovista1.exceptions.BusinessException;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.exceptions.EventNotFoundException;
import com.ouadia.rovista1.exceptions.FavorieNotFoundException;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.interfaces.IFavorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//@RestController
@RequestMapping("/api/client/favories")
@RequiredArgsConstructor
public class FavorieController {

    private final SecurityUtils securityUtils;
    private final IFavorieService favoriteService;

    // GET /api/client/favories?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<HistoriqueFavorieDto>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam int page,
            @RequestParam int size) {

        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(favoriteService.getFavories(userId, page, size));
    }

    // POST /api/client/favorites/{eventId}
    @PostMapping("/{eventId}")
    public ResponseEntity<FavorieResponseDto> addFavorite(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetails userDetails) throws BusinessException, ClientNotFoundException, EventNotFoundException, FavorieNotFoundException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoriteService.addFavorie(securityUtils.getCurrentUserId(), eventId));
    }

    // DELETE /api/client/favorites/{eventId}
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        favoriteService.removeFavorie(securityUtils.getCurrentUserId(), eventId);
        return ResponseEntity.noContent().build(); // 204
    }

}