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
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String label;

    @Relationship(type = "HAS_ENVIRONMENT", direction = Relationship.Direction.OUTGOING)
    private Set<Environment> environments = new HashSet<>();
}
