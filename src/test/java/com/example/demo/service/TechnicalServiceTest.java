package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Service; // Model class
import com.example.demo.repository.EnvironmentRepository;
import com.example.demo.repository.ServiceRepository; // Repository for Service
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
public class TechnicalServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private EnvironmentRepository environmentRepository; // Dependency of TechnicalService

    @InjectMocks
    private TechnicalService technicalService; // The service being tested

    private Service service1;
    private Service service2;

    @BeforeEach
    void setUp() {
        service1 = new Service();
        service1.setId(1L); // Changed to Long
        service1.setName("Main Service");
        service1.setType("BACKEND");
        service1.setUsedServices(new HashSet<>());

        service2 = new Service();
        service2.setId(2L); // Changed to Long
        service2.setName("Dependent Service");
        service2.setType("DATABASE");
    }

    @Test
    void createService_shouldSaveAndReturnService() {
        // Arrange
        when(serviceRepository.save(any(Service.class))).thenReturn(service1);

        // Act
        Service createdService = technicalService.createService(service1);

        // Assert
        assertNotNull(createdService);
        assertEquals("Main Service", createdService.getName());
        verify(serviceRepository, times(1)).save(service1);
    }

    @Test
    void getServiceById_shouldReturnService_whenFound() {
        // Arrange
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service1));

        // Act
        Service foundService = technicalService.getServiceById(1L);

        // Assert
        assertNotNull(foundService);
        assertEquals("Main Service", foundService.getName());
        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void getServiceById_shouldThrowResourceNotFound_whenNotFound() {
        // Arrange
        when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            technicalService.getServiceById(99L);
        });
        verify(serviceRepository, times(1)).findById(99L);
    }

    @Test
    void addUsedService_shouldAddDependencyAndSaveService() {
        // Arrange
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service1));
        when(serviceRepository.findById(2L)).thenReturn(Optional.of(service2));
        // service1.getUsedServices().add(service2) will be done by the service method
        when(serviceRepository.save(any(Service.class))).thenReturn(service1);


        // Act
        Service updatedService = technicalService.addUsedService(1L, 2L);

        // Assert
        assertNotNull(updatedService);
        assertTrue(updatedService.getUsedServices().contains(service2));
        assertEquals(1, updatedService.getUsedServices().size());
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).findById(2L);
        verify(serviceRepository, times(1)).save(service1);
    }
}
