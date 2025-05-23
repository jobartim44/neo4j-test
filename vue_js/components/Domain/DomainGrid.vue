<script setup lang="ts">
  import { useDomainFields } from '@/Entities/def/DomainDef';
  import { type IDomain } from '@/Entities/interface/IDomain';
  import { MinioManager } from '@/Entities/manager/DomainManager';
  import { ref } from 'vue';
  import {
    EPnvColumnPinnedPosition,
    PnvBasePageGrid,
    ResponsiveScreen,
    useRouterStore,
    type IPnvAction,
    type IPnvColumn,
  } from 'vue-pnv-components-library';
  
  /* Ref */
  const listDomains = ref<IDomain[]>([]);
  const loadingRef = ref(false);
  /* CL */
  const columnsAvailable: IPnvColumn[] = [
    { ...useDomainFields().id, pinned: EPnvColumnPinnedPosition.LEFT },
    useDomainFields().name,
  ];
  const columnsActive: IPnvColumn[] = [
    useDomainFields().id,
	useDomainFields().name,
  ];

  /* Actions */
  

  /* Init */
  loadDomainList();

  /* Fonction */
  async function loadDomainList(): Promise<void> {
    loadingRef.value = true;
    await DomainManager.getAllDomains(listDomains.value);
    loadingRef.value = false;
  }
</script>
<template>
  <PnvBasePageGrid
    id="grid-domain"
    title="Liste des domaines"
    :search-fields="[useDomainFields().name]"
    :datas="listDomains"
    :columns-active="columnsActive"
    :columns-available="columnsAvailable"
    :sortable="true"
    :fields-id="[useDomainFields().id]"
    :th-draggable-activate="true"
    :resize-column-activate="true"
    :filter-th-visible="true"
    :full-front-activate="true"
    :is-loading="loadingRef"
    :responsive-screen-width="ResponsiveScreen.L"
  />
</template>
