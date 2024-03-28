<template>
  <div class="segmentation">
    <GrowthInner />
  </div>
</template>

<script setup>
import "leaflet/dist/leaflet.css";
import GrowthInner from "./growthInner.vue";
</script>

<style scoped lang="scss">
.segmentation {
  width: 100%;
  height: calc(100vh - 96px);
}
</style>
