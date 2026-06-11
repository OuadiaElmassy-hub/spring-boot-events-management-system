package com.ouadia.rovista1.controllers;




import com.ouadia.rovista1.dtos.client.ClientRequestDto;
import com.ouadia.rovista1.dtos.client.ClientResponseDto;
import com.ouadia.rovista1.exceptions.ClientNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.services.interfaces.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {
    IClientService clientService;


    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto client) throws ReservationNotFoundException {
        return new ResponseEntity<>((clientService.addClient(client)), HttpStatus.CREATED);
    }


    // racherche client
    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDto>RechercheClient(@PathVariable("id") Long id)throws ClientNotFoundException {
        return  ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>>GetAllClient()throws ClientNotFoundException {
        return  ResponseEntity.ok(clientService.getAllClients());
    }
    //update client
    @PutMapping("{id}")
    public ResponseEntity<ClientResponseDto>UpdateClient (@PathVariable("id") Long id,
                                                          @RequestBody ClientRequestDto client
    ) throws ClientNotFoundException, ReservationNotFoundException {
        return ResponseEntity.ok(clientService.editClient(client,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> DeleteClient (@PathVariable("id") Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.ok("client deleted successfully ! ✅");
    }


}
