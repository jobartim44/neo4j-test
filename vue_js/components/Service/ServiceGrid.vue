<script setup lang="ts">
  import { useServiceFields } from '@/Entities/def/ServiceDef';
  import { type IService } from '@/Entities/interface/IService';
  import { ServiceManager } from '@/Entities/manager/ServiceManager';
  import { ref } from 'vue';
  import {
    EPnvColumnPinnedPosition,
    PnvBasePageGrid,
    ResponsiveScreen,
    // useRouterStore, // Uncomment if needed for actions
    // type IPnvAction, // Uncomment if actions are added
    type IPnvColumn,
  } from 'vue-pnv-components-library';
  
  /* Ref */
  const listServices = ref<IService[]>([]);
  const loadingRef = ref(false);

  /* Columns */
  const srvFields = useServiceFields(); // Renamed to avoid conflict
  const columnsAvailable: IPnvColumn[] = [
    { ...srvFields.id, pinned: EPnvColumnPinnedPosition.LEFT },
    srvFields.name,
    srvFields.description,
    srvFields.version,
    srvFields.status,
    // Add other service fields here if they should be available for selection
  ];
  const columnsActive: IPnvColumn[] = [
    srvFields.id,
    srvFields.name,
    srvFields.description,
    srvFields.version,
    srvFields.status,
    // Add other service fields here if they should be active by default
  ];

  /* Actions */
  // Define actions if needed
  // const actions: IPnvAction[] = [ ... ];

  /* Init */
  loadServiceList();

  /* Function */
  async function loadServiceList(): Promise<void> {
    loadingRef.value = true;
    await ServiceManager.getAllServices(listServices); // Pass the ref directly
    loadingRef.value = false;
  }
</script>
<template>
  <PnvBasePageGrid
    id="grid-service"
    title="Liste des Services"
    :search-fields="[srvFields.name, srvFields.description, srvFields.status]" // Search by name, description, status
    :datas="listServices"
    :columns-active="columnsActive"
    :columns-available="columnsAvailable"
    :sortable="true"
    :fields-id="[srvFields.id]" // Assuming 'id' is the unique identifier
    :th-draggable-activate="true"
    :resize-column-activate="true"
    :filter-th-visible="true"
    :full-front-activate="true"
    :is-loading="loadingRef"
    :responsive-screen-width="ResponsiveScreen.L"
    
    class="h-full w-full"
    grid-header-class="bg-gray-100"
    grid-body-class="overflow-y-auto"
    row-class="hover:bg-blue-100"

    // pagination-activate="true"
    // :pagination-item-per-page="10"
    // :pagination-total-items="listServices.length"

    // edit-mode-activate="true"
    // @on-save-row="handleSaveService"

    // selection-activate="true"
    // @on-selection-change="handleSelectionChange"
  />
</template>
