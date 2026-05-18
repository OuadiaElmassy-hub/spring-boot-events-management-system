package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Client;
import com.ouadia.rovista1.repositories.ClientRepository;
import com.ouadia.rovista1.services.IClientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

    private ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client addClient(Client client) {
        return repository.save(client);
    }

    @Override
    public Client editClient(Client client) {
        return repository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    @Override
    public void deleteClientById(Long id) {
        repository.deleteById(id);
    }
}
