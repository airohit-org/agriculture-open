<template>
  <div 
    ref="gisMapRef"
    class="map-container"
  ></div>
</template>

<script setup>
import { useGisMap } from './use-gis-map';
import "leaflet/dist/leaflet.css";
import "@geoman-io/leaflet-geoman-free";
import '@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css';

const props = defineProps({
  options: {
    type: Object,
    default: () => ({})
  }
})
const emit = defineEmits(['load'])
const { state, createMap, ...api } = useGisMap();
const { map } = toRefs(state);
// DOM
const gisMapRef = ref();

onMounted(() => {
  const { center, ...config } = props.options;
  createMap(gisMapRef.value, config, center);
  emit('load', map.value)
})

defineExpose({ ...api })

</script>

<style scoped lang="scss">
.map-container {
  width: 100%;
  height: 100%;
}
</style>

