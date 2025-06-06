package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node
@Data
public class Domain
{

	@Id
	@GeneratedValue
	private Long id;

	private String label;

	@Relationship(type = "HAS_CLIENT", direction = Relationship.Direction.OUTGOING)
	private Set<Client> clients = new HashSet<>();
}
