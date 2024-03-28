<template>
  <GisMap ref="mapRef" @load="mapLoad" :options="options"></GisMap>
</template>

<script setup>
import GisMap from '@/components/GisMap/GisMap.vue'
import { createPolygon, zoomAndCenterExactArr } from '../help'
import { debounceOneline as debounce } from '@/utils';
import { nextTick, onMounted, onUnmounted, ref,  watch } from 'vue';

const props = defineProps({
  farm: Array,
  landList: Array,
  farmName: String,
})
const mapRef = ref(null)
const mapInstance = ref(null)
const farmLayer = ref()
const layers = ref([])

const mapLoad = (map) => {
  mapInstance.value = map
}

function drawFarm() {
  if (farmLayer.value) {
    return;
  }
  farmLayer.value = createPolygon(props.farm, {
    color: "#ffffff",
    fill: false,
    weight: 4,
  });
  mapInstance.value.addLayer(farmLayer.value);
  onResize();
}
function drawLandList() {
  if (layers.value.length) {
    return;
  }
  props.landList?.forEach(({ color, pos }) => {
    const p = createPolygon(pos, { color, weight: 2})
    p.addTo(mapInstance.value);
    layers.value.push(p);
  });
  onResize();
}
function onResize(){
  debounce(() => {
    (farmLayer.value?.getLatLngs() || layers.value.length) && zoomAndCenterExactArr(
      mapInstance.value,
      farmLayer.value?.getLatLngs(),
      layers.value.concat(farmLayer.value),
      [100, 100]
    );
  }, 500)();
}

onMounted(() => {
  window.addEventListener("resize", onResize);
})
onUnmounted(() => {
  window.removeEventListener("resize", onResize);
})

watch(() => props.farm, () => {
  nextTick(() => {
    if(farmLayer.value) return
    drawFarm()
  })
}, {deep:true})

watch(() => props.landList, () => {
  nextTick(() => {
    if(layers.value.length) return
    drawLandList()
  })
}, {deep:true})

const options = reactive({
  locale: 'zh',
  zoom: 5,
  center: [34.269701, 108.942146],
  // dragging: false,
  scrollWheelZoom: false,
  attributionControl: false,
  doubleClickZoom: false,
})
</script>