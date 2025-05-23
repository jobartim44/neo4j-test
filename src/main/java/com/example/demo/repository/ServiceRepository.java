package com.example.demo.repository;

import com.example.demo.model.Service;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ServiceRepository extends Neo4jRepository<Service, Long> {
}
