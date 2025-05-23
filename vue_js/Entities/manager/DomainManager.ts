import { type Ref } from 'vue';
import { EntityManager, HHttp, HObject, useNotificationStore } from 'vue-pnv-components-library';
import { type IDomain } from '../interface/IDomain';
import { type IClient } from '../interface/IClient'; // Import IClient

const URL_API = import.meta.env.VITE_URL_API;
// Assuming API_KEY is globally available or defined elsewhere, like in env variables
const API_KEY = import.meta.env.VITE_API_KEY; 

export const DomainManager = {
  ...EntityManager,
  /* Récupère la liste des domaines */
    async getAllDomains(listDomain: Ref<Array<IDomain>>): Promise<void> {
      try {
        const response = await HHttp.get(`${URL_API}/api/domains`, {
          headers: {
            apikey: API_KEY,
          },
        });

        if (response.status === 200 || response.status === 204) {
          const jsonData = await response.json();

          listDomain.value = [];
          jsonData.forEach((domainIter: IDomain) => {
            listDomain.value.push(domainIter);
          });
        } else {
          useNotificationStore().open('Erreur chargement données');
        }
        listDomain.value.sort((a: IDomain, b: IDomain) => {
          // Ensure 'id' exists and is a string for localeCompare, or adjust sorting logic
          return (a.id && b.id) ? a.id.localeCompare(b.id) : 0;
        });
      } catch (error) {
        useNotificationStore().open(error as string);
      }
    },

  /* Crée un nouveau domaine */
  async createDomain(domain: IDomain): Promise<IDomain | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/domains`, domain, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 201) { // 201 Created
        return await response.json();
      } else {
        useNotificationStore().open('Erreur création domaine');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  /* Récupère un domaine par son ID */
  async getDomainById(id: string): Promise<IDomain | null> {
    try {
      const response = await HHttp.get(`${URL_API}/api/domains/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) {
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement domaine');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  /* Met à jour un domaine */
  async updateDomain(id: string, domainDetails: IDomain): Promise<IDomain | null> {
    try {
      const response = await HHttp.put(`${URL_API}/api/domains/${id}`, domainDetails, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) {
        return await response.json();
      } else {
        useNotificationStore().open('Erreur mise à jour domaine');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  /* Supprime un domaine */
  async deleteDomain(id: string): Promise<boolean> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/domains/${id}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 204) { // 204 No Content
        return true;
      } else {
        useNotificationStore().open('Erreur suppression domaine');
        return false;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return false;
    }
  },

  /* Ajoute un client à un domaine */
  async addClientToDomain(domainId: string, client: IClient): Promise<IDomain | null> {
    try {
      const response = await HHttp.post(`${URL_API}/api/domains/${domainId}/clients`, client, {
        headers: {
          'Content-Type': 'application/json',
          apikey: API_KEY,
        },
      });
      if (response.status === 200) { // Or 201 if client is created
        return await response.json();
      } else {
        useNotificationStore().open('Erreur ajout client au domaine');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  /* Supprime un client d'un domaine */
  async removeClientFromDomain(domainId: string, clientId: string): Promise<IDomain | null> {
    try {
      const response = await HHttp.delete(`${URL_API}/api/domains/${domainId}/clients/${clientId}`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) {
        return await response.json();
      } else {
        useNotificationStore().open('Erreur suppression client du domaine');
        return null;
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return null;
    }
  },

  /* Récupère les clients d'un domaine */
  async getClientsForDomain(domainId: string): Promise<Array<IClient>> {
    try {
      const response = await HHttp.get(`${URL_API}/api/domains/${domainId}/clients`, {
        headers: {
          apikey: API_KEY,
        },
      });
      if (response.status === 200) {
        return await response.json();
      } else {
        useNotificationStore().open('Erreur chargement clients du domaine');
        return [];
      }
    } catch (error) {
      useNotificationStore().open(error as string);
      return [];
    }
  },
};

export type IDomainManager = typeof DomainManager;
