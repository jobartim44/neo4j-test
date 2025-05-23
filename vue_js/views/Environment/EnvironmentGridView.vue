<template>
  <div class="environment-grid-view-container pa-4">
    <h1 class="text-h5 mb-4">Environment Management</h1>
    <EnvironmentGrid />
  </div>
</template>

<script setup lang="ts">
import EnvironmentGrid from '@/components/Environment/EnvironmentGrid.vue'; // Adjusted path
</script>

<style scoped>
.environment-grid-view-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}
/* Add any view-specific styles here */
</style>
