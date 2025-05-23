import { type Ref } from 'vue';
import { EntityManager, HHttp, HObject } from 'vue-pnv-components-library';
import { type IDomain } from '../interface/IDomain';

const URL_API = import.meta.env.VITE_URL_API;

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
          return a.id.localeCompare(b.id);
        });
      } catch (error) {
        useNotificationStore().open(error as string);
      }
    },
};

export type IDomainManager = typeof DomainManager;
