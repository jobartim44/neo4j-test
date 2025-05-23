package com.example.demo.controller;

import com.example.demo.model.Service; // Assuming this is the correct model name from previous steps
import com.example.demo.service.TechnicalService; // Corrected service name
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final TechnicalService technicalService; // Corrected service name

    // Service CRUD
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        Service createdService = technicalService.createService(service);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        Service service = technicalService.getServiceById(id);
        return ResponseEntity.ok(service);
    }

    @GetMapping
    public ResponseEntity<Iterable<Service>> getAllServices() {
        Iterable<Service> services = technicalService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        Service updatedService = technicalService.updateService(id, serviceDetails);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        technicalService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    // USES_SERVICE relationship management
    @PostMapping("/{serviceId}/uses/{usedServiceId}")
    public ResponseEntity<Service> addUsedService(@PathVariable Long serviceId, @PathVariable Long usedServiceId) {
        Service updatedService = technicalService.addUsedService(serviceId, usedServiceId);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{serviceId}/uses/{usedServiceId}")
    public ResponseEntity<Service> removeUsedService(@PathVariable Long serviceId, @PathVariable Long usedServiceId) {
        Service updatedService = technicalService.removeUsedService(serviceId, usedServiceId);
        return ResponseEntity.ok(updatedService);
    }

    @GetMapping("/{serviceId}/uses")
    public ResponseEntity<Set<Service>> getUsedServices(@PathVariable Long serviceId) {
        Set<Service> usedServices = technicalService.getUsedServices(serviceId);
        return ResponseEntity.ok(usedServices);
    }

    @GetMapping("/{serviceId}/users")
    public ResponseEntity<Set<Service>> getServiceUsers(@PathVariable Long serviceId) {
        Set<Service> serviceUsers = technicalService.getServiceUsers(serviceId);
        return ResponseEntity.ok(serviceUsers);
    }
}
