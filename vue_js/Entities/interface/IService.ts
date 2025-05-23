export interface IService {
  id: string; // Or number, matching backend's ID type
  name: string;
  description?: string;
  version?: string; // Example: service version
  status?: string; // Example: service status (e.g., 'UP', 'DOWN', 'DEGRADED')
  // For USES_SERVICE relationship: a service can use other services
  usedServices?: IService[]; // Array of services that this service uses
  // For the inverse relationship (users of this service), if needed for display
  // This might be populated by getServiceUsers endpoint
  serviceUsers?: IService[]; 
  // Add other service properties as needed based on Service.java model
}
