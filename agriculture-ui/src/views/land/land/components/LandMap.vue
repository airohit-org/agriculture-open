<template>
  <GisMap ref="mapRef" @load="mapLoad" :options="options"/>
</template>

<script setup>
import GisMap from '@/components/GisMap/index.vue'
import { createPolygon } from '../help'
import { nextTick, watch } from 'vue';

const props = defineProps({
  farm: {
    type: Array,
    default: () => []
  },
  landList: {
    type: Array,
    default: () => []
  }
})

const mapInstance = ref(null)
const mapRef = ref(null)
const farmLayer = ref(null)
const landLayer = ref(null)


watch(()=> props.farm, (newVal) => {
  nextTick(() => {    
    farmLayer.value = createPolygon(props.farm, {
      color: "#ffffff",
      fill: false,
      weight: 4,
    })
    console.log(farmLayer.value)
    mapInstance.value.addLayer(farmLayer.value)
  })
})


const options = reactive({
  locale: 'zh',
  zoom: 5,
  center: [34.269701, 108.942146],
  scrollWheelZoom: false,
  attributionControl: false,
  doubleClickZoom: false,
})
const mapLoad = (map) => {
  mapInstance.value = map
}
</script>