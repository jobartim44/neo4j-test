package com.example.demo.repository;

import com.example.demo.model.Environment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EnvironmentRepository extends Neo4jRepository<Environment, Long> {
}
