package com.ouadia.rovista1.services;


import com.ouadia.rovista1.entities.Client;

import java.util.List;

public interface IClientService {
    public Client addClient(Client client);
    public Client editClient(Client client);
    public Client getClientById(Long id);
    public List<Client> getAllClients();
    public void deleteClientById(Long id);
}
