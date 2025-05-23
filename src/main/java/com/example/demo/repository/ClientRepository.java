package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClientRepository extends Neo4jRepository<Client, Long> {
}
