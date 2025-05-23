package com.example.demo.repository;

import com.example.demo.model.Domain;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DomainRepository extends Neo4jRepository<Domain, Long> {
}
