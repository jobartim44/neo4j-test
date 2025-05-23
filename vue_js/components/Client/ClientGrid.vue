<script setup lang="ts">
  import { useClientFields } from '@/Entities/def/ClientDef';
  import { type IClient } from '@/Entities/interface/IClient';
  import { ClientManager } from '@/Entities/manager/ClientManager';
  import { ref } from 'vue';
  import {
    EPnvColumnPinnedPosition,
    PnvBasePageGrid,
    ResponsiveScreen,
    // useRouterStore, // Not used in DomainGrid, uncomment if needed for actions
    // type IPnvAction, // Uncomment if actions are added
    type IPnvColumn,
  } from 'vue-pnv-components-library';
  
  /* Ref */
  const listClients = ref<IClient[]>([]);
  const loadingRef = ref(false);

  /* Columns */
  const clientFields = useClientFields();
  const columnsAvailable: IPnvColumn[] = [
    { ...clientFields.id, pinned: EPnvColumnPinnedPosition.LEFT },
    clientFields.name,
    clientFields.description,
    // Add other client fields here if they should be available for selection
  ];
  const columnsActive: IPnvColumn[] = [
    clientFields.id,
    clientFields.name,
    clientFields.description,
    // Add other client fields here if they should be active by default
  ];

  /* Actions */
  // Define actions if needed, similar to DomainGrid if it had any
  // const actions: IPnvAction[] = [ ... ];

  /* Init */
  loadClientList();

  /* Function */
  async function loadClientList(): Promise<void> {
    loadingRef.value = true;
    await ClientManager.getAllClients(listClients); // Pass the ref directly
    loadingRef.value = false;
  }
</script>
<template>
  <PnvBasePageGrid
    id="grid-client"
    title="Liste des Clients"
    :search-fields="[clientFields.name, clientFields.description]" // Search by name and description
    :datas="listClients"
    :columns-active="columnsActive"
    :columns-available="columnsAvailable"
    :sortable="true"
    :fields-id="[clientFields.id]" // Assuming 'id' is the unique identifier
    :th-draggable-activate="true"
    :resize-column-activate="true"
    :filter-th-visible="true"
    :full-front-activate="true" // Enable full front-end features like sorting, filtering if not server-side
    :is-loading="loadingRef"
    :responsive-screen-width="ResponsiveScreen.L"
    
    class="h-full w-full" // Example of adding classes for styling
    grid-header-class="bg-gray-100" // Example of styling grid header
    grid-body-class="overflow-y-auto" // Example for scrollable body
    row-class="hover:bg-blue-100" // Example for row hover effect

    // pagination-activate="true" // Uncomment if pagination is needed
    // :pagination-item-per-page="10" // Set items per page for pagination
    // :pagination-total-items="listClients.length" // Set total items for pagination (if front-end pagination)

    // edit-mode-activate="true" // Uncomment if inline editing is needed
    // @on-save-row="handleSaveClient" // Handle save event for inline editing

    // selection-activate="true" // Uncomment if row selection is needed
    // @on-selection-change="handleSelectionChange" // Handle selection change event
  />
</template>
