<script setup lang="ts">
  import { useEnvironmentFields } from '@/Entities/def/EnvironmentDef';
  import { type IEnvironment } from '@/Entities/interface/IEnvironment';
  import { EnvironmentManager } from '@/Entities/manager/EnvironmentManager';
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
  const listEnvironments = ref<IEnvironment[]>([]);
  const loadingRef = ref(false);

  /* Columns */
  const envFields = useEnvironmentFields(); // Renamed to avoid conflict
  const columnsAvailable: IPnvColumn[] = [
    { ...envFields.id, pinned: EPnvColumnPinnedPosition.LEFT },
    envFields.name,
    envFields.description,
    // Add other environment fields here if they should be available for selection
  ];
  const columnsActive: IPnvColumn[] = [
    envFields.id,
    envFields.name,
    envFields.description,
    // Add other environment fields here if they should be active by default
  ];

  /* Actions */
  // Define actions if needed
  // const actions: IPnvAction[] = [ ... ];

  /* Init */
  loadEnvironmentList();

  /* Function */
  async function loadEnvironmentList(): Promise<void> {
    loadingRef.value = true;
    await EnvironmentManager.getAllEnvironments(listEnvironments); // Pass the ref directly
    loadingRef.value = false;
  }
</script>
<template>
  <PnvBasePageGrid
    id="grid-environment"
    title="Liste des Environnements"
    :search-fields="[envFields.name, envFields.description]" // Search by name and description
    :datas="listEnvironments"
    :columns-active="columnsActive"
    :columns-available="columnsAvailable"
    :sortable="true"
    :fields-id="[envFields.id]" // Assuming 'id' is the unique identifier
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
    // :pagination-total-items="listEnvironments.length"

    // edit-mode-activate="true"
    // @on-save-row="handleSaveEnvironment"

    // selection-activate="true"
    // @on-selection-change="handleSelectionChange"
  />
</template>
