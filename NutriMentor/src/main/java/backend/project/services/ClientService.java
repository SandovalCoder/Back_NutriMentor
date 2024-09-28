package backend.project.services;

import backend.project.entities.Client;

import java.util.List;

public interface ClientService {
    Client insertClient(Client client);  // Aquí ya trabajas directamente con la entidad Client
    Client updateClient(Long id, Client client);  // Actualización con la entidad Client
    void deleteClient(Long id);
    Client findById(Long id);
    List<Client> findAll();
}
