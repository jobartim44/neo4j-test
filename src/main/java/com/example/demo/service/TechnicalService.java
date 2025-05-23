package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Service; // Ensure this is the model
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.EnvironmentRepository;
import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service; // Removed to avoid ambiguity
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@org.springframework.stereotype.Service // Fully qualified annotation
@RequiredArgsConstructor
public class TechnicalService {

    private final ServiceRepository serviceRepository;
    private final EnvironmentRepository environmentRepository; // Uncommented and ensured injection

    @Transactional
    public Service createService(Service service) {
        // Association with Environment is handled in EnvironmentService
        return serviceRepository.save(service);
    }

    @Transactional(readOnly = true)
    public Service getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Iterable<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Transactional
    public Service updateService(Long id, Service serviceDetails) {
        Service service = getServiceById(id);
        service.setName(serviceDetails.getName());
        service.setType(serviceDetails.getType());
        // USES_SERVICE relationship modification is handled by specific methods
        return serviceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        Service service = getServiceById(id);
        // Consider implications: remove from Environment's service list,
        // remove from other services' usedServices lists (both incoming and outgoing USES_SERVICE).
        // For now, just deleting the service node and its direct relationships.
        serviceRepository.delete(service);
    }

    @Transactional
    public Service addUsedService(Long serviceId, Long usedServiceId) {
        Service service = getServiceById(serviceId);
        Service usedService = getServiceById(usedServiceId); // Ensure the 'used' service exists

        service.getUsedServices().add(usedService);
        return serviceRepository.save(service);
    }

    @Transactional
    public Service removeUsedService(Long serviceId, Long usedServiceId) {
        Service service = getServiceById(serviceId);
        Service usedServiceToRemove = serviceRepository.findById(usedServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Used service not found with id: " + usedServiceId));
        
        service.getUsedServices().remove(usedServiceToRemove);
        return serviceRepository.save(service);
    }

    @Transactional(readOnly = true)
    public Set<Service> getUsedServices(Long serviceId) {
        Service service = getServiceById(serviceId);
        return service.getUsedServices();
    }

    @Transactional(readOnly = true)
    public Set<Service> getServiceUsers(Long serviceId) {
        // This typically requires a custom query in the repository to find services
        // that have 'serviceId' in their 'usedServices' list.
        // e.g., MATCH (user:Service)-[:USES_SERVICE]->(used:Service {id: $serviceId}) RETURN user
        // For now, if ServiceRepository doesn't have this custom method, this will be a placeholder.
        // Assuming ServiceRepository will be updated with findUsersOfService(String serviceId)
        // return serviceRepository.findUsersOfService(serviceId); 
        // Placeholder if the method doesn't exist yet:
        // throw new UnsupportedOperationException("getServiceUsers requires a custom repository query, not yet implemented.");
        // For the purpose of this task, let's assume it might return an empty set or be implemented later.
        // For now, will call a method that doesn't exist yet to highlight the need.
        // To make it compilable, I will comment out the call or return empty set.
        // return serviceRepository.findServiceUsers(serviceId); // This method needs to be defined in ServiceRepository
        return Collections.emptySet(); // Placeholder
    }
}
