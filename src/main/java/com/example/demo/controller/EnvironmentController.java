package com.example.demo.controller;

import com.example.demo.model.Environment;
import com.example.demo.model.Service;
import com.example.demo.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/environments")
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    // Environment CRUD
    @PostMapping
    public ResponseEntity<Environment> createEnvironment(@RequestBody Environment environment) {
        Environment createdEnvironment = environmentService.createEnvironment(environment);
        return new ResponseEntity<>(createdEnvironment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Environment> getEnvironmentById(@PathVariable Long id) {
        Environment environment = environmentService.getEnvironmentById(id);
        return ResponseEntity.ok(environment);
    }

    @GetMapping
    public ResponseEntity<Iterable<Environment>> getAllEnvironments() {
        Iterable<Environment> environments = environmentService.getAllEnvironments();
        return ResponseEntity.ok(environments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Environment> updateEnvironment(@PathVariable Long id, @RequestBody Environment environmentDetails) {
        Environment updatedEnvironment = environmentService.updateEnvironment(id, environmentDetails);
        return ResponseEntity.ok(updatedEnvironment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable Long id) {
        environmentService.deleteEnvironment(id);
        return ResponseEntity.noContent().build();
    }

    // Service management for an Environment
    @PostMapping("/{environmentId}/services")
    public ResponseEntity<Environment> addServiceToEnvironment(@PathVariable Long environmentId, @RequestBody Service service) {
        Environment updatedEnvironment = environmentService.addServiceToEnvironment(environmentId, service);
        return ResponseEntity.ok(updatedEnvironment); // Or CREATED if service is newly created
    }

    @DeleteMapping("/{environmentId}/services/{serviceId}")
    public ResponseEntity<Environment> removeServiceFromEnvironment(@PathVariable Long environmentId, @PathVariable Long serviceId) {
        Environment updatedEnvironment = environmentService.removeServiceFromEnvironment(environmentId, serviceId);
        return ResponseEntity.ok(updatedEnvironment);
    }

    @GetMapping("/{environmentId}/services")
    public ResponseEntity<Set<Service>> getServicesForEnvironment(@PathVariable Long environmentId) {
        Set<Service> services = environmentService.getServicesForEnvironment(environmentId);
        return ResponseEntity.ok(services);
    }
}
