import { type Ref } from 'vue';
import { EntityManager, HHttp, useNotificationStore } from 'vue-pnv-components-library';
import { type IService } from '../interface/IService';

const URL_API = import.meta.env.VITE_URL_API;
const API_KEY = import.meta.env.VITE_API_KEY; // Ensure this is configured in your .env file

export const ServiceManager = {
  ...EntityManager,

  // Service CRUD
  async createService(service: IService): Promise<IService | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/services`, service, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 201) { // Created
        return await response.json();
      } else {
        useNotificationStore().open('Erreur création service');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getServiceById(id: string): Promise<IService | null> {
    try {
      const response = await HHttp.get(`${URL_API}/api/services/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement service');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getAllServices(listService: Ref<Array<IService>>): Promise<void> {
    try {
      const response = await HHttp.get(`${URL_API}/api/services`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200 || response.status === 204) { // OK or No Content
        const jsonData = await response.json();
        listService.value = [];
        if (jsonData) {
          jsonData.forEach((serviceIter: IService) => {
            listService.value.push(serviceIter);
          });
        }
        listService.value.sort((a: IService, b: IService) => {
          return (a.name && b.name) ? a.name.localeCompare(b.name) : 0;
        });
      } else {
        useNotificationStore().open('Erreur chargement des services');
      }
    } catch (error) {
      useNotificationStore().open(error as string);
    }
  },

  async updateService(id: string, serviceDetails: IService): Promise<IService | null> {
    try {
      const response = await HHttp.put(`${URL_API}/api/services/${id}`, serviceDetails, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur mise à jour service');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async deleteService(id: string): Promise<boolean> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/services/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 204) { // No Content
        return true;
      } else {
        useNotificationStore().open('Erreur suppression service');
        return false;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return false;
    }
  },

  // USES_SERVICE relationship management
  async addUsedService(serviceId: string, usedServiceId: string): Promise<IService | null> {
    try {
      // The request body for this might be empty or might require specific structure
      // Assuming empty body for now as per typical REST patterns for such links
      const response = await HHttp.post(`${URL_API}/api/services/${serviceId}/uses/${usedServiceId}`, {}, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur ajout service utilisé');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async removeUsedService(serviceId: string, usedServiceId: string): Promise<IService | null> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/services/${serviceId}/uses/${usedServiceId}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json(); // Or potentially 204 No Content, adjust as per actual API
      } else {
        useNotificationStore().open('Erreur suppression service utilisé');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  async getUsedServices(serviceId: string): Promise<Array<IService>> {
    try {
      const response = await HHttp.get(`${URL_API}/api/services/${serviceId}/uses`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement services utilisés');
        return [];
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return [];
    }
  },

  async getServiceUsers(serviceId: string): Promise<Array<IService>> {
    try {
      const response = await HHttp.get(`${URL_API}/api/services/${serviceId}/users`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // OK
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement utilisateurs du service');
        return [];
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return [];
    }
  },
};

export type IServiceManager = typeof ServiceManager;
