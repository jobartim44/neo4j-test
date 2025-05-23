<template>
  <div class="client-grid-view-container pa-4">
    <h1 class="text-h5 mb-4">Client Management</h1>
    <ClientGrid />
  </div>
</template>

<script setup lang="ts">
import ClientGrid from '@/components/Client/ClientGrid.vue'; // Adjusted path
</script>

<style scoped>
.client-grid-view-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}
/* Add any view-specific styles here */
</style>
