package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.Domain;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DomainService {

    private final DomainRepository domainRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Domain createDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    @Transactional(readOnly = true)
    public Domain getDomainById(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domain not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    @Transactional
    public Domain updateDomain(Long id, Domain domainDetails) {
        Domain domain = getDomainById(id);
        domain.setCode(domainDetails.getCode());
        domain.setLabel(domainDetails.getLabel());
        // Note: client list modification is handled by specific methods
        return domainRepository.save(domain);
    }

    @Transactional
    public void deleteDomain(Long id) {
        Domain domain = getDomainById(id);
        // Consider cascading deletes or handling related entities if necessary
        domainRepository.delete(domain);
    }

    @Transactional
    public Domain addClientToDomain(Long domainId, Client client) {
        Domain domain = getDomainById(domainId);
        // Save the client first if it's new or needs to be persisted independently
        Client savedClient = clientRepository.save(client);
        domain.getClients().add(savedClient);
        return domainRepository.save(domain);
    }

    @Transactional
    public Domain removeClientFromDomain(Long domainId, Long clientId) {
        Domain domain = getDomainById(domainId);
        Client clientToRemove = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
        domain.getClients().remove(clientToRemove);
        return domainRepository.save(domain);
    }

    @Transactional(readOnly = true)
    public Set<Client> getClientsForDomain(Long domainId) {
        Domain domain = getDomainById(domainId);
        return domain.getClients();
    }
}
