# API Documentation

This document provides a brief overview of the available API endpoints.

## 1. Domain API (`/api/domains`)

Manages domains and their associated clients.

*   **`POST /api/domains`**
    *   Description: Creates a new domain.
    *   Request Body: Domain data (JSON representation of the Domain entity).
*   **`GET /api/domains/{id}`**
    *   Description: Retrieves a specific domain by its ID.
*   **`GET /api/domains`**
    *   Description: Retrieves all domains.
*   **`PUT /api/domains/{id}`**
    *   Description: Updates an existing domain by its ID.
    *   Request Body: Domain data (JSON representation of the Domain entity with updated fields).
*   **`DELETE /api/domains/{id}`**
    *   Description: Deletes a specific domain by its ID.
*   **`POST /api/domains/{domainId}/clients`**
    *   Description: Adds a client to a specific domain.
    *   Request Body: Client data (JSON representation of the Client entity).
*   **`DELETE /api/domains/{domainId}/clients/{clientId}`**
    *   Description: Removes a client from a specific domain.
*   **`GET /api/domains/{domainId}/clients`**
    *   Description: Retrieves all clients for a specific domain.

## 2. Client API (`/api/clients`)

Manages clients and their associated environments.

*   **`POST /api/clients`**
    *   Description: Creates a new client.
    *   Request Body: Client data (JSON representation of the Client entity).
*   **`GET /api/clients/{id}`**
    *   Description: Retrieves a specific client by its ID.
*   **`GET /api/clients`**
    *   Description: Retrieves all clients.
*   **`PUT /api/clients/{id}`**
    *   Description: Updates an existing client by its ID.
    *   Request Body: Client data (JSON representation of the Client entity with updated fields).
*   **`DELETE /api/clients/{id}`**
    *   Description: Deletes a specific client by its ID.
*   **`POST /api/clients/{clientId}/environments`**
    *   Description: Adds an environment to a specific client.
    *   Request Body: Environment data (JSON representation of the Environment entity).
*   **`DELETE /api/clients/{clientId}/environments/{environmentId}`**
    *   Description: Removes an environment from a specific client.
*   **`GET /api/clients/{clientId}/environments`**
    *   Description: Retrieves all environments for a specific client.

## 3. Environment API (`/api/environments`)

Manages environments and their associated services.

*   **`POST /api/environments`**
    *   Description: Creates a new environment.
    *   Request Body: Environment data (JSON representation of the Environment entity).
*   **`GET /api/environments/{id}`**
    *   Description: Retrieves a specific environment by its ID.
*   **`GET /api/environments`**
    *   Description: Retrieves all environments.
*   **`PUT /api/environments/{id}`**
    *   Description: Updates an existing environment by its ID.
    *   Request Body: Environment data (JSON representation of the Environment entity with updated fields).
*   **`DELETE /api/environments/{id}`**
    *   Description: Deletes a specific environment by its ID.
*   **`POST /api/environments/{environmentId}/services`**
    *   Description: Adds a service to a specific environment.
    *   Request Body: Service data (JSON representation of the Service entity).
*   **`DELETE /api/environments/{environmentId}/services/{serviceId}`**
    *   Description: Removes a service from a specific environment.
*   **`GET /api/environments/{environmentId}/services`**
    *   Description: Retrieves all services for a specific environment.

## 4. Service API (`/api/services`)

Manages technical services and their dependencies on other services.

*   **`POST /api/services`**
    *   Description: Creates a new service.
    *   Request Body: Service data (JSON representation of the Service entity).
*   **`GET /api/services/{id}`**
    *   Description: Retrieves a specific service by its ID.
*   **`GET /api/services`**
    *   Description: Retrieves all services.
*   **`PUT /api/services/{id}`**
    *   Description: Updates an existing service by its ID.
    *   Request Body: Service data (JSON representation of the Service entity with updated fields).
*   **`DELETE /api/services/{id}`**
    *   Description: Deletes a specific service by its ID.
*   **`POST /api/services/{serviceId}/uses/{usedServiceId}`**
    *   Description: Adds a dependency (uses relationship) from one service to another.
*   **`DELETE /api/services/{serviceId}/uses/{usedServiceId}`**
    *   Description: Removes a dependency (uses relationship) from one service to another.
*   **`GET /api/services/{serviceId}/uses`**
    *   Description: Retrieves all services that a specific service uses (dependencies).
*   **`GET /api/services/{serviceId}/users`**
    *   Description: Retrieves all services that use a specific service (dependents).
