package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.Domain; // Keep if needed for future, but not in current constructor
import com.example.demo.model.Environment;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.DomainRepository; // Ensure this is injected
import com.example.demo.repository.EnvironmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final DomainRepository domainRepository; // Injected as per requirements
    private final EnvironmentRepository environmentRepository; // For add/remove/get Environment methods


    @Transactional
    public Client createClient(Client client) {
        // If client needs to be associated with a domain upon creation,
        // that logic would be handled here or in DomainService.
        // For now, just saving the client.
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client updateClient(Long id, Client clientDetails) {
        Client client = getClientById(id);
        client.setCode(clientDetails.getCode());
        client.setLabel(clientDetails.getLabel());
        // Environment list modification is handled by specific methods
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = getClientById(id);
        // Before deleting a client, consider implications:
        // 1. Remove it from any Domain's client list.
        // This might require fetching all domains, iterating, and removing this client.
        // Or, if DomainService.removeClientFromDomain is called, that handles it.
        // For simplicity, we'll assume this is handled by the caller or another service.
        // 2. Delete its associated environments or handle them.
        // For now, just deleting the client node and its direct relationships.

    // Also, when a client is deleted, it should be removed from any domain's client list.
    // This requires domainRepository.
    // Example:
    // List<Domain> domains = (List<Domain>) domainRepository.findAll(); // This could be inefficient
    // for (Domain domain : domains) {
    //    if (domain.getClients().removeIf(c -> c.getId().equals(id))) {
    //        domainRepository.save(domain);
    //    }
    // }
    // A better approach might be a custom query or ensuring DomainService handles this.
    // For now, focusing on the direct deletion.
        clientRepository.delete(client);
    }

    @Transactional
    public Client addEnvironmentToClient(Long clientId, Environment environment) {
        Client client = getClientById(clientId);
        // Save the environment first if it's new or needs to be persisted independently
        Environment savedEnvironment = environmentRepository.save(environment); // EnvironmentRepository is needed here
        client.getEnvironments().add(savedEnvironment);
        return clientRepository.save(client);
    }

    @Transactional
    public Client removeEnvironmentFromClient(Long clientId, Long environmentId) {
        Client client = getClientById(clientId);
        Environment environmentToRemove = environmentRepository.findById(environmentId) // EnvironmentRepository is needed here
                .orElseThrow(() -> new ResourceNotFoundException("Environment not found with id: " + environmentId));
        client.getEnvironments().remove(environmentToRemove);
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Set<Environment> getEnvironmentsForClient(Long clientId) {
        Client client = getClientById(clientId);
        return client.getEnvironments();
    }
}
