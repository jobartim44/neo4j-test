import { type Ref } from 'vue';
import { EntityManager, HHttp, useNotificationStore } from 'vue-pnv-components-library';
import { type IClient } from '../interface/IClient';
import { type IEnvironment } from '../interface/IEnvironment';

const URL_API = import.meta.env.VITE_URL_API;
const API_KEY = import.meta.env.VITE_API_KEY; // Ensure this is configured in your .env file

export const ClientManager = {
  ...EntityManager,

  // Client CRUD
  async createClient(client: IClient): Promise<IClient | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/clients`, client, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 201) { // Created
        return await response.json();
      } else {
        useNotificationStore().open('Erreur création client');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getClientById(id: string): Promise<IClient | null> {
    try {
      const response = await HHttp.get(`${URL_API}/api/clients/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement client');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getAllClients(listClient: Ref<Array<IClient>>): Promise<void> {
    try {
      const response = await HHttp.get(`${URL_API}/api/clients`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200 || response.status === 204) { // OK or No Content
        const jsonData = await response.json();
        listClient.value = [];
        if (jsonData) {
          jsonData.forEach((clientIter: IClient) => {
            listClient.value.push(clientIter);
          });
        }
        // Sort if needed, e.g., by name
        listClient.value.sort((a: IClient, b: IClient) => {
          return (a.name && b.name) ? a.name.localeCompare(b.name) : 0;
        });
      } else {
        useNotificationStore().open('Erreur chargement des clients');
      }
    } catch (error) {
      useNotificationStore().open(error as string);
    }
  },

  async updateClient(id: string, clientDetails: IClient): Promise<IClient | null> {
    try {
      const response = await HHttp.put(`${URL_API}/api/clients/${id}`, clientDetails, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur mise à jour client');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async deleteClient(id: string): Promise<boolean> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/clients/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 204) { // No Content
        return true;
      } else {
        useNotificationStore().open('Erreur suppression client');
        return false;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return false;
    }
  },

  // Environment management for a Client
  async addEnvironmentToClient(clientId: string, environment: IEnvironment): Promise<IClient | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/clients/${clientId}/environments`, environment, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK (or 201 if env is newly created)
        return await response.json();
      } else {
        useNotificationStore().open('Erreur ajout environnement au client');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async removeEnvironmentFromClient(clientId: string, environmentId: string): Promise<IClient | null> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/clients/${clientId}/environments/${environmentId}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur suppression environnement du client');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getEnvironmentsForClient(clientId: string): Promise<Array<IEnvironment>> {
    try {
      const response = await HHttp.get(`${URL_API}/api/clients/${clientId}/environments`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement environnements du client');
        return [];
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return [];
    }
  },
};

export type IClientManager = typeof ClientManager;
