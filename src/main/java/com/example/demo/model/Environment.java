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
public class Environment {

    @Id
    @GeneratedValue
    private Long id;

    private String name; // (e.g., PROD, TEST, QA)

    @Relationship(type = "HAS_SERVICE", direction = Relationship.Direction.OUTGOING)
    private Set<Service> services = new HashSet<>();
}
