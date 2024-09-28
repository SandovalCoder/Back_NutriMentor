package backend.project.serviceimpl;

import backend.project.dtos.DTOClient;
import backend.project.entities.Client;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.ClientRepository;
import backend.project.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client insertClient(Client client) {
        // Verificamos si el email ya está registrado
        if (isEmailRegistered(client.getEmail())) {
            throw new KeyRepeatedDataException("Email is already registered.");
        }
        return clientRepository.save(client);  // Guardamos directamente la entidad Client
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client existingClient = findById(id);

        if (client.getEmail() != null && !client.getEmail().isBlank()) {
            if (!existingClient.getEmail().equals(client.getEmail()) && isEmailRegistered(client.getEmail())) {
                throw new KeyRepeatedDataException("Email is already registered.");
            }
            existingClient.setEmail(client.getEmail());
        }

        existingClient.setName(client.getName());
        existingClient.setAddress(client.getAddress());

        return clientRepository.save(existingClient);  // Actualizamos y guardamos la entidad actualizada
    }

    @Override
    public void deleteClient(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    private boolean isEmailRegistered(String email) {
        return clientRepository.findByEmail(email).isPresent();
    }
}
