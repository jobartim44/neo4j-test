package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Environment;
import com.example.demo.model.Service; // This is the model class for Service
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.EnvironmentRepository;
import com.example.demo.repository.ServiceRepository; // This is the repository for Service
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
public class EnvironmentServiceTest {

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private ServiceRepository serviceRepository; // Mock for ServiceRepository

    @Mock
    private ClientRepository clientRepository; // Mock for ClientRepository

    @InjectMocks
    private EnvironmentService environmentService;

    private Environment environment;
    private Service service; // Model instance for Service

    @BeforeEach
    void setUp() {
        environment = new Environment();
        environment.setId(1L); // Changed to Long
        environment.setName("PROD");
        environment.setServices(new HashSet<>());

        service = new Service(); // Initialize Service object
        service.setId(101L); // Changed to Long
        service.setName("Database Service");
        service.setType("DATABASE");
    }

    @Test
    void createEnvironment_shouldSaveAndReturnEnvironment() {
        // Arrange
        when(environmentRepository.save(any(Environment.class))).thenReturn(environment);

        // Act
        Environment createdEnvironment = environmentService.createEnvironment(environment);

        // Assert
        assertNotNull(createdEnvironment);
        assertEquals("PROD", createdEnvironment.getName());
        verify(environmentRepository, times(1)).save(environment);
    }

    @Test
    void getEnvironmentById_shouldReturnEnvironment_whenFound() {
        // Arrange
        when(environmentRepository.findById(1L)).thenReturn(Optional.of(environment));

        // Act
        Environment foundEnvironment = environmentService.getEnvironmentById(1L);

        // Assert
        assertNotNull(foundEnvironment);
        assertEquals("PROD", foundEnvironment.getName());
        verify(environmentRepository, times(1)).findById(1L);
    }

    @Test
    void getEnvironmentById_shouldThrowResourceNotFound_whenNotFound() {
        // Arrange
        when(environmentRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            environmentService.getEnvironmentById(99L);
        });
        verify(environmentRepository, times(1)).findById(99L);
    }

    @Test
    void addServiceToEnvironment_shouldAddServiceAndSaveEnvironment() {
        // Arrange
        when(environmentRepository.findById(1L)).thenReturn(Optional.of(environment));
        when(serviceRepository.save(any(Service.class))).thenReturn(service); // serviceRepository saves the service
        when(environmentRepository.save(any(Environment.class))).thenReturn(environment); // environmentRepository saves the environment

        // Act
        Environment updatedEnvironment = environmentService.addServiceToEnvironment(1L, service);

        // Assert
        assertNotNull(updatedEnvironment);
        assertTrue(updatedEnvironment.getServices().contains(service));
        assertEquals(1, updatedEnvironment.getServices().size());
        verify(environmentRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).save(service);
        verify(environmentRepository, times(1)).save(environment);
    }
}
