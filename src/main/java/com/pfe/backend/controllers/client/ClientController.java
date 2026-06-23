package com.pfe.backend.controllers.client;

import com.pfe.backend.dtos.StatistiquesResponseDto;
import com.pfe.backend.dtos.client.ClientPublicInfoResponseDto;
import com.pfe.backend.dtos.client.ClientRequestDto;
import com.pfe.backend.dtos.client.ClientResponseDto;
import com.pfe.backend.exceptions.ClientNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.security.SecurityUtils;
import com.pfe.backend.services.client.ClientDashboardService;
import com.pfe.backend.services.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    final IClientService service;
    final SecurityUtils securityUtils;

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto client) throws ReservationNotFoundException {
        return new ResponseEntity<>((service.addClient(client)), HttpStatus.CREATED);
    }

    @GetMapping("/public/clients/{id}")
    public ClientPublicInfoResponseDto getPublicInfoClient(@PathVariable Long id) throws ClientNotFoundException {
        return service.getInfoClientForPublic(id);
    }

    private final ClientDashboardService dashboardService;

    @GetMapping("/client/dashboard/stats")
    public ResponseEntity<StatistiquesResponseDto> getStats(
            @AuthenticationPrincipal UserDetails userDetails) throws ClientNotFoundException {

        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(dashboardService.getStats(userId));
    }

    // racherche client
    @GetMapping("/admin/dashboard/clients/{id}")
    public ResponseEntity<ClientResponseDto>RechercheClient(@PathVariable("id") Long id)throws ClientNotFoundException {
        return  ResponseEntity.ok(service.getClientById(id));
    }

    @GetMapping("/admin/dashboard/clients")
    public ResponseEntity<List<ClientResponseDto>>GetAllClient()throws ClientNotFoundException {
        return  ResponseEntity.ok(service.getAllClients());
    }
    //update client
    @PutMapping("/admin/dashboard/clients/{id}")
    public ResponseEntity<ClientResponseDto>UpdateClient (@PathVariable("id") Long id,
                                                          @RequestBody ClientRequestDto client
    ) throws ClientNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(service.editClient(client,id));
    }

    @DeleteMapping("/admin/dashboard/clients/{id}")
    public ResponseEntity<String> DeleteClient (@PathVariable("id") Long id){
        service.deleteClientById(id);
        return ResponseEntity.ok("client deleted successfully ! ✅");
    }
}
