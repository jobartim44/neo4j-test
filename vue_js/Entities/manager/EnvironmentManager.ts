import { type Ref } from 'vue';
import { EntityManager, HHttp, useNotificationStore } from 'vue-pnv-components-library';
import { type IEnvironment } from '../interface/IEnvironment';
import { type IService } from '../interface/IService'; // Assuming IService is defined

const URL_API = import.meta.env.VITE_URL_API;
const API_KEY = import.meta.env.VITE_API_KEY; // Ensure this is configured in your .env file

export const EnvironmentManager = {
  ...EntityManager,

  // Environment CRUD
  async createEnvironment(environment: IEnvironment): Promise<IEnvironment | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/environments`, environment, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 201) { // Created
        return await response.json();
      } else {
        useNotificationStore().open('Erreur création environnement');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getEnvironmentById(id: string): Promise<IEnvironment | null> {
    try {
      const response = await HHttp.get(`${URL_API}/api/environments/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement environnement');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getAllEnvironments(listEnvironment: Ref<Array<IEnvironment>>): Promise<void> {
    try {
      const response = await HHttp.get(`${URL_API}/api/environments`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200 || response.status === 204) { // OK or No Content
        const jsonData = await response.json();
        listEnvironment.value = [];
        if (jsonData) {
          jsonData.forEach((envIter: IEnvironment) => {
            listEnvironment.value.push(envIter);
          });
        }
        listEnvironment.value.sort((a: IEnvironment, b: IEnvironment) => {
          return (a.name && b.name) ? a.name.localeCompare(b.name) : 0;
        });
      } else {
        useNotificationStore().open('Erreur chargement des environnements');
      }
    } catch (error) {
      useNotificationStore().open(error as string);
    }
  },

  async updateEnvironment(id: string, environmentDetails: IEnvironment): Promise<IEnvironment | null> {
    try {
      const response = await HHttp.put(`${URL_API}/api/environments/${id}`, environmentDetails, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur mise à jour environnement');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async deleteEnvironment(id: string): Promise<boolean> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/environments/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 204) { // No Content
        return true;
      } else {
        useNotificationStore().open('Erreur suppression environnement');
        return false;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return false;
    }
  },

  // Service management for an Environment
  async addServiceToEnvironment(environmentId: string, service: IService): Promise<IEnvironment | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/environments/${environmentId}/services`, service, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK (or 201 if service is newly created)
        return await response.json();
      } else {
        useNotificationStore().open('Erreur ajout service à l\'environnement');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async removeServiceFromEnvironment(environmentId: string, serviceId: string): Promise<IEnvironment | null> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/environments/${environmentId}/services/${serviceId}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur suppression service de l\'environnement');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getServicesForEnvironment(environmentId: string): Promise<Array<IService>> {
    try {
      const response = await HHttp.get(`${URL_API}/api/environments/${environmentId}/services`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement services de l\'environnement');
        return [];
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return [];
    }
  },
};

export type IEnvironmentManager = typeof EnvironmentManager;
