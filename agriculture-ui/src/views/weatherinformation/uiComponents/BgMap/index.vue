<template>
  <div ref="mapContainer">
    <div class="popup-container" ref="popup" v-show="popupShow">
      <img
        class="popup-container-topBorder"
        src="https://oss.airoteach.cn/9a8e5334fb73ee89c95f8c3944949d75c9d2d98ec36a9d6df8b6d52e84551b3e.png"
      />
      <MapPopup title="气象站" :info="info" :popupShow="popupShow" />
      <img
        class="popup-container-bottomBorder"
        src="https://oss.airoteach.cn/4aaf6ace8e56d96655f34b1831d967be42aabcf6e6118d3f559162c647497dfe.png"
      />
    </div>
  </div>
</template>
<script setup>
import {
  createMap,
  zoomAndCenterExactArr,
  createImgOverlay,
  createPolygon,
  createMarkerIcon,
  createGrid,
} from "./help";
import MapPopup from "../MapPopup/index.vue";
import { LAYERS } from "../../help";
import { diffArray } from "@/utils";
import "leaflet/dist/leaflet.css";
import { proxyRefs } from "vue";
// import { getLatlngByPopup } from "../../../device/help";

const props = defineProps({
  farmDevice: Array,
  farmName: String,
  farm: Array,
  progressIndex: Number,
  radarInfo: Array,
  layerValues: Array,
  landList: Array,
});
const mapContainer = ref();
const deviceLayerGroup = ref();
const popup = ref();
const farmLayer = ref(null);
const info = ref([]);
const layers = ref([]);
const map = ref(null);
const gridLayer = ref(null);
const imgOverlay = ref(null);
const popupShow = ref(false);
const layerValuesMiddle = computed(() => {
  return JSON.stringify(props.layerValues);
});
const { proxy } = getCurrentInstance();
const emit = defineEmits(["clickLand"]);
watch(
  () => props.farm,
  () => {
    proxy.$nextTick(() => {
      initMap();
      drawFarm();
    });
  }
);
watch(
  () => props.landList,
  () => {
    proxy.$nextTick(() => {
      initMap();
      drawLandList();
    });
  },
  { deep: true, immediate: true }
);
watch(layerValuesMiddle, (oldV, newV) => {
  handleLayers(diffArray(JSON.parse(oldV), JSON.parse(newV)));
});
watch(
  () => props.progressIndex,
  () => {
    changeImage();
  }
);

function initMap() {
  if (map.value) {
    return;
  }
  map.value = createMap(mapContainer.value);
}
function gotoFarmCenter() {
  setTimeout(() => {
    zoomAndCenterExactArr(
      map.value,
      farmLayer.value.getLatLngs(),
      [farmLayer.value],
      [100, 100]
    );
  }, 500);
}
function drawFarmDevice() {
  props.farmDevice.forEach((item) => {
    const { coordinate } = item || {};
    const iconUrl =
      "https://oss.airoteach.cn/498072fdbd98a576c02cf1310c14ea3d20e1f844e06585213de14241a4b0342c.png";
    const p = L.marker(coordinate, {
      icon: createMarkerIcon({ iconUrl }),
    });
    p.on("click", (e) => bindPopup(e, item));
    deviceLayerGroup.value.addLayer(p);
  });
}

function bindPopup({ latlng: { lat, lng } = {} }, item) {
  const { deviceName } = item || {};
  info.value = {
    deviceType: deviceName,
    deviceInjectType: "API",
    deviceIndex: "-",
  };
  popupShow.value = true;
  const popup = L.popup();
  popup.setLatLng({ lat, lng }).setContent(popup.value).openOn(map.value);
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
  map.value.addLayer(farmLayer.value);
  gotoFarmCenter();
}

function drawGrid() {
  if (gridLayer.value) {
    return;
  }
  gridLayer.value = createGrid({
    bounds: props.farm,
  });
  gridLayer.value && map.value.addLayer(gridLayer.value);
}

function changeImage() {
  const imgUrl = props.radarInfo?.[props.progressIndex]?.src;
  imgOverlay.value?.setUrl(imgUrl);
}
function handleLayers({ more, less }) {
  const match = {
    [LAYERS.RADAR.id]: {
      handleAdd: handleAddRadar,
      handleRemove: handleRemoveRadar,
    },
    [LAYERS.DEVICE.id]: {
      handleAdd: handleAddDevice,
      handleRemove: handleRemoveDevice,
    },
    [LAYERS.NET.id]: {
      handleAdd: handleAddNet,
      handleRemove: handleRemoveNet,
    }
  };
  more.forEach((id) => match[id].handleRemove());
  less.forEach((id) => match[id].handleAdd());
}

function handleAddRadar() {
  imgOverlay.value = createImgOverlay(map.value);
  changeImage();
}
function handleRemoveRadar() {
  try {
    map.value.removeLayer(imgOverlay.value);
  } catch (error) {}
}

function handleAddDevice() {
  deviceLayerGroup.value = L.layerGroup([]);
  deviceLayerGroup.value.addTo(map.value);
  drawFarmDevice();
}
function handleRemoveDevice() {
  map.value.removeLayer(deviceLayerGroup.value);
  popupShow.value = false;
}
function drawLandList() {
  if (layers.value.length) {
    return;
  }
  props.landList?.forEach(({ color, pos, id }) => {
    const p = createPolygon(pos, { color, weight: 2 }, id, () => {
      emit("clickLand", id);
    });
    p.addTo(map.value);
    layers.value.push(p);
  });
}
function handleAddNet() {
  drawGrid();
}
function handleRemoveNet() {
  map.value.removeLayer(gridLayer.value);
  gridLayer.value = null;
}

onMounted(() => {
  initMap();
});
</script>
<style scoped lang="scss">
@import "./popup.scss";
div {
  width: 100%;
  height: 100%;
}
</style>
