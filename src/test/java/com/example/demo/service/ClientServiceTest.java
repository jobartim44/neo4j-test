package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.Environment;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.DomainRepository;
import com.example.demo.repository.EnvironmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private DomainRepository domainRepository; // Even if not directly used in these specific tests, it's part of ClientService's dependencies

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private Environment environment;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L); // Changed to Long
        client.setCode("CLI1");
        client.setLabel("Client One");
        client.setEnvironments(new HashSet<>());

        environment = new Environment();
        environment.setId(101L); // Changed to Long
        environment.setName("PROD");
    }

    @Test
    void createClient_shouldSaveAndReturnClient() {
        // Arrange
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client createdClient = clientService.createClient(client);

        // Assert
        assertNotNull(createdClient);
        assertEquals("CLI1", createdClient.getCode());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getClientById_shouldReturnClient_whenFound() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClientById(1L);

        // Assert
        assertNotNull(foundClient);
        assertEquals("CLI1", foundClient.getCode());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getClientById_shouldThrowResourceNotFound_whenNotFound() {
        // Arrange
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.getClientById(99L);
        });
        verify(clientRepository, times(1)).findById(99L);
    }

    @Test
    void addEnvironmentToClient_shouldAddEnvironmentAndSaveClient() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(environmentRepository.save(any(Environment.class))).thenReturn(environment);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client updatedClient = clientService.addEnvironmentToClient(1L, environment);

        // Assert
        assertNotNull(updatedClient);
        assertTrue(updatedClient.getEnvironments().contains(environment));
        assertEquals(1, updatedClient.getEnvironments().size());
        verify(clientRepository, times(1)).findById(1L);
        verify(environmentRepository, times(1)).save(environment);
        verify(clientRepository, times(1)).save(client);
    }
}
