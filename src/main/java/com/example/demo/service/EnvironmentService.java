package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Environment;
import com.example.demo.model.Service; // Ensure this is the model
import com.example.demo.repository.EnvironmentRepository;
import com.example.demo.repository.ServiceRepository; // Needed for add/remove/get Service methods
import com.example.demo.repository.ClientRepository; // As per requirements
import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service; // Removed to avoid ambiguity
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@org.springframework.stereotype.Service // Fully qualified annotation
@RequiredArgsConstructor
public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;
    private final ServiceRepository serviceRepository; // Retained for managing services
    private final ClientRepository clientRepository; // Injected as per requirements

    @Transactional
    public Environment createEnvironment(Environment environment) {
        // Similar to ClientService, association with Client can be handled here or in ClientService
        return environmentRepository.save(environment);
    }

    @Transactional(readOnly = true)
    public Environment getEnvironmentById(Long id) {
        return environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Environment not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Environment> getAllEnvironments() {
        return environmentRepository.findAll();
    }

    @Transactional
    public Environment updateEnvironment(Long id, Environment environmentDetails) {
        Environment environment = getEnvironmentById(id);
        environment.setName(environmentDetails.getName());
        // Service list modification is handled by specific methods
        return environmentRepository.save(environment);
    }

    @Transactional
    public void deleteEnvironment(Long id) {
        Environment environment = getEnvironmentById(id);
        // Consider implications: remove from Client's environment list, delete associated services.
        // For now, just deleting the environment node.
        environmentRepository.delete(environment);
    }

    @Transactional
    public Environment addServiceToEnvironment(Long environmentId, Service service) {
        Environment environment = getEnvironmentById(environmentId);
        // Save the service first if it's new or needs to be persisted independently
        Service savedService = serviceRepository.save(service);
        environment.getServices().add(savedService);
        return environmentRepository.save(environment);
    }

    @Transactional
    public Environment removeServiceFromEnvironment(Long environmentId, Long serviceId) {
        Environment environment = getEnvironmentById(environmentId);
        Service serviceToRemove = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
        environment.getServices().remove(serviceToRemove);
        return environmentRepository.save(environment);
    }

    @Transactional(readOnly = true)
    public Set<Service> getServicesForEnvironment(Long environmentId) {
        Environment environment = getEnvironmentById(environmentId);
        return environment.getServices();
    }
}
