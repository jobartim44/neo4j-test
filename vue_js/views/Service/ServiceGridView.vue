<template>
  <div class="service-grid-view-container pa-4">
    <h1 class="text-h5 mb-4">Service Management</h1>
    <ServiceGrid />
  </div>
</template>

<script setup lang="ts">
import ServiceGrid from '@/components/Service/ServiceGrid.vue'; // Adjusted path
</script>

<style scoped>
.service-grid-view-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}
/* Add any view-specific styles here */
</style>
