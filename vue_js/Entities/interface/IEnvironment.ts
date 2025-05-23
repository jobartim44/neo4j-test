import { type IService } from './IService'; // Assuming IService is defined

export interface IEnvironment {
  id: string; // Or number, matching backend's ID type
  name: string;
  description?: string; // Example: adding an optional description
  // Add other environment properties as needed based on Environment.java model
  services?: IService[]; // Representing the Set<Service> relationship
}
