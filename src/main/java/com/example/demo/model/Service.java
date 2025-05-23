package com.example.demo.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
public class Service {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type; // (e.g., DATABASE, S3, KEYCLOAK, FRONTEND, BACKEND, GATEWAY)

    @Relationship(type = "USES_SERVICE", direction = Relationship.Direction.OUTGOING)
    private Set<Service> usedServices = new HashSet<>();
}
