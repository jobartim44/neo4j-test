package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Environment;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // Client CRUD
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<Iterable<Client>> getAllClients() {
        Iterable<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client updatedClient = clientService.updateClient(id, clientDetails);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // Environment management for a Client
    @PostMapping("/{clientId}/environments")
    public ResponseEntity<Client> addEnvironmentToClient(@PathVariable Long clientId, @RequestBody Environment environment) {
        Client updatedClient = clientService.addEnvironmentToClient(clientId, environment);
        return ResponseEntity.ok(updatedClient); // Could be CREATED if env is newly created
    }

    @DeleteMapping("/{clientId}/environments/{environmentId}")
    public ResponseEntity<Client> removeEnvironmentFromClient(@PathVariable Long clientId, @PathVariable Long environmentId) {
        Client updatedClient = clientService.removeEnvironmentFromClient(clientId, environmentId);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/{clientId}/environments")
    public ResponseEntity<Set<Environment>> getEnvironmentsForClient(@PathVariable Long clientId) {
        Set<Environment> environments = clientService.getEnvironmentsForClient(clientId);
        return ResponseEntity.ok(environments);
    }
}
