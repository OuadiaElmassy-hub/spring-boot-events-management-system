package com.ouadia.rovista1.controllers.client;

import com.ouadia.rovista1.dtos.StatistiquesResponseDto;
import com.ouadia.rovista1.dtos.client.ClientPublicInfoResponseDto;
import com.ouadia.rovista1.dtos.client.ClientRequestDto;
import com.ouadia.rovista1.dtos.client.ClientResponseDto;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.security.SecurityUtils;
import com.ouadia.rovista1.services.client.ClientDashboardService;
import com.ouadia.rovista1.services.interfaces.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    IClientService service;
    SecurityUtils securityUtils;

    @PostMapping
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        return service.addClient(clientRequestDto);
    }

    @GetMapping("/public/clients/{id}")
    public ClientPublicInfoResponseDto getPublicInfoClient(@PathVariable Long id) throws ClientNotFoundException {
        return service.getInfoClientForPublic(id);
    }

    private final ClientDashboardService dashboardService;

    @GetMapping("/dashboard/stats")
    public ResponseEntity<StatistiquesResponseDto> getStats(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(dashboardService.getStats(userId));
    }

}
