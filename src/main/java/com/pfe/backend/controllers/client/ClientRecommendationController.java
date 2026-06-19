package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.RecommendationResponseDto;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.client.ClientRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/recommendations")
@RequiredArgsConstructor
public class ClientRecommendationController {

    private final ClientRecommendationService recommendationService;
    private final SecurityUtils securityUtils;
    // GET /api/client/recommendations?page=0&size=9
    @GetMapping
    public ResponseEntity<Page<RecommendationResponseDto>> getRecommendations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = securityUtils.getCurrentUserId();;
        return ResponseEntity.ok(
            recommendationService.getRecommendations(userId, page, size));
    }
}