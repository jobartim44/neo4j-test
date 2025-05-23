import { type IEnvironment } from './IEnvironment'; // Assuming IEnvironment will be defined

export interface IClient {
  id: string; // Or number, matching backend's ID type
  name: string;
  // Add other client properties as needed based on Client.java model
  description?: string; // Example: adding an optional description
  environments?: IEnvironment[]; // Representing the Set<Environment> relationship
}
