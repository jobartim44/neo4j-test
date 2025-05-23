package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Domain;
import com.example.demo.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    // Domain CRUD
    @PostMapping
    public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) {
        Domain createdDomain = domainService.createDomain(domain);
        return new ResponseEntity<>(createdDomain, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domain> getDomainById(@PathVariable Long id) {
        Domain domain = domainService.getDomainById(id);
        return ResponseEntity.ok(domain);
    }

    @GetMapping
    public ResponseEntity<Iterable<Domain>> getAllDomains() {
        Iterable<Domain> domains = domainService.getAllDomains();
        return ResponseEntity.ok(domains);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Domain> updateDomain(@PathVariable Long id, @RequestBody Domain domainDetails) {
        Domain updatedDomain = domainService.updateDomain(id, domainDetails);
        return ResponseEntity.ok(updatedDomain);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable Long id) {
        domainService.deleteDomain(id);
        return ResponseEntity.noContent().build();
    }

    // Client management for a Domain
    @PostMapping("/{domainId}/clients")
    public ResponseEntity<Domain> addClientToDomain(@PathVariable Long domainId, @RequestBody Client client) {
        Domain updatedDomain = domainService.addClientToDomain(domainId, client);
        return ResponseEntity.ok(updatedDomain); // Could also be CREATED if client is newly created by this op
    }

    @DeleteMapping("/{domainId}/clients/{clientId}")
    public ResponseEntity<Domain> removeClientFromDomain(@PathVariable Long domainId, @PathVariable Long clientId) {
        Domain updatedDomain = domainService.removeClientFromDomain(domainId, clientId);
        return ResponseEntity.ok(updatedDomain);
    }

    @GetMapping("/{domainId}/clients")
    public ResponseEntity<Set<Client>> getClientsForDomain(@PathVariable Long domainId) {
        Set<Client> clients = domainService.getClientsForDomain(domainId);
        return ResponseEntity.ok(clients);
    }
}
