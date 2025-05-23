package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.Domain;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.DomainRepository;

@ExtendWith(MockitoExtension.class)
public class DomainServiceTest
{

	@Mock
	private DomainRepository domainRepository;

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private DomainService domainService;

	private Domain domain;
	private Client client;

	@BeforeEach
	void setUp()
	{
		domain = new Domain();
		domain.setId(1L); // Changed to Long
		domain.setLabel("Domain One");
		domain.setClients(new HashSet<>()); // Ensure clients set is initialized

		client = new Client();
		client.setId(101L); // Changed to Long
		client.setLabel("Client One");
	}

	@Test
	void createDomain_shouldSaveAndReturnDomain()
	{
		// Arrange
		when(domainRepository.save(any(Domain.class))).thenReturn(domain);

		// Act
		Domain createdDomain = domainService.createDomain(domain);

		// Assert
		assertNotNull(createdDomain);
		verify(domainRepository, times(1)).save(domain);
	}

	@Test
	void getDomainById_shouldReturnDomain_whenFound()
	{
		// Arrange
		when(domainRepository.findById(1L)).thenReturn(Optional.of(domain));

		// Act
		Domain foundDomain = domainService.getDomainById(1L);

		// Assert
		assertNotNull(foundDomain);
		verify(domainRepository, times(1)).findById(1L);
	}

	@Test
	void getDomainById_shouldThrowResourceNotFound_whenNotFound()
	{
		// Arrange
		when(domainRepository.findById(99L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () ->
		{
			domainService.getDomainById(99L);
		});
		verify(domainRepository, times(1)).findById(99L);
	}

	@Test
	void addClientToDomain_shouldAddClientAndSaveDomain()
	{
		// Arrange
		when(domainRepository.findById(1L)).thenReturn(Optional.of(domain));
		when(clientRepository.save(any(Client.class))).thenReturn(client); // ClientRepository saves the client
		when(domainRepository.save(any(Domain.class))).thenReturn(domain); // DomainRepository saves the domain with the new client

		// Act
		Domain updatedDomain = domainService.addClientToDomain(1L, client);

		// Assert
		assertNotNull(updatedDomain);
		assertTrue(updatedDomain.getClients().contains(client));
		assertEquals(1, updatedDomain.getClients().size());
		verify(domainRepository, times(1)).findById(1L);
		verify(clientRepository, times(1)).save(client);
		verify(domainRepository, times(1)).save(domain);
	}
}
