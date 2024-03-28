<template>
  <AdaptLayout :loading="loading">
    <template v-slot:bg>
      <BgMap
        :farmDevice="farmDevice"
        :farmName="farmName"
        ref="map"
        :landList="landList"
        :layerValues="layerValues"
        :radarInfo="radarInfo"
        :progressIndex="progressIndex"
        :farm="farm"
        @clickLand="handleClickLand"
      />
    </template>
    <template v-slot:center>
      <FarmTitle :title="farmName" />
    </template>
    <template v-slot:left-1>
      <Weather24hour :twentyFourHoursInfo="weather24" />
    </template>
    <template v-slot:left-2>
      <Weather10Day :echartsInfo="weather10" />
    </template>
    <template v-slot:left-3>
      <WaterDownHour
        :today="today"
        :place="farmName"
        :msgList="msgList"
        :echartsInfo="{ timeDataInfo, rainfallInfo }"
      />
    </template>
    <template v-slot:right-1>
      <TopTool
        :values="showIcons"
        :layerValues="layerValues"
        @clickTool="handleTools"
        @handleLayer="handleLayer"
      />
    </template>
    <template v-slot:right-2>
      <WeatherInfo :show="weatherinfoAnimation" :values="currentData" />
    </template>
    <template v-slot:bottom>
      <TimeLine
        ref="timelineRef"
        :progress="progress"
        @onProgress="handleProgress"
        :radarInfo="radarInfo"
        @openRadar="openRadar"
      />
    </template>
    <template v-slot:loading>
      <BLoading />
    </template>
  </AdaptLayout>
</template>
<script setup>
import FarmTitle from "../overview/components/FarmTitle/index.vue";
import AdaptLayout from "./uiComponents/AdaptLayout/index.vue";
import BgMap from "./uiComponents/BgMap/index.vue";
import Weather24hour from "./uiComponents/Weather24hour/index.vue";
import Weather10Day from "./uiComponents/Weather10Day/index.vue";
import WaterDownHour from "./uiComponents/WaterDownHour/index.vue";
import TopTool from "./uiComponents/TopTool/index.vue";
import WeatherInfo from "./uiComponents/WeatherInfo/index.vue";
import TimeLine from "./uiComponents/TimeLine/index.vue";
import BLoading from "@/components/BLoading/index.vue";
import { MOCK, LAYERS, formatRadarInfo } from "./help";
import { preloadImg } from "@/utils/index";
import { iplocateAndFit } from "@/views/land/land/help.js";
import { calcWeatherList } from "./uiComponents/WeatherInfo/help";
// import { createNamespacedHelpers } from "vuex";
import { getWeatherGrid } from "@/api/weather";
import useWeatherStore from "@/store/modules/weather";
const weatherStore = useWeatherStore();
import useBaseMapStore from "@/store/modules/baseMap";
const baseMapStore = useBaseMapStore();
// const { mapState, mapActions } = createNamespacedHelpers("weather/");
const timelineRef = ref();
const map = ref();
const weatherinfoAnimation = ref(false);
// 取第一个
const activeLandId = ref(-1);
const progress = ref(0);
const layerValues = ref([]);
const loading = ref(false);
const { farmName, farm, landList } = toRefs(baseMapStore);
const {
  farmDevice,
  msgList,
  weather10,
  weather24,
  timeDataInfo,
  rainfallInfo,
  today,
  initGridId,
  showIcons,
  radarInfo,
  grid,
} = toRefs(weatherStore);
const { proxy } = getCurrentInstance();
const currentData = computed(() => {
  return calcWeatherList(grid.value?.[activeLandId.value]);
});
const progressIndex = computed(() => {
  return ~~(((radarInfo.value.length - 1) * progress.value) / 100);
});

function fresh() {
  loading.value = true;
  Promise.all([weatherStore.getWeatherInfo(), baseMapStore.getMapInfo()])
    .then(() => {
      loading.value = false;
      initActiveLandId();
    })
    .catch(() => {
      loading.value = false;
    });
}

function initActiveLandId() {
  activeLandId.value = initGridId.value;
}

function handleProgress(v) {
  progress.value = v;
}

function openRadar() {
  !layerValues.value.includes(LAYERS.RADAR.id) &&
    layerValues.value.push(LAYERS.RADAR.id);
}

function onCloseRadar() {
  timelineRef.value?.pause();
}
function handleLayer(currentIndex) {
  const has = layerValues.value.includes(currentIndex);
  if (has) {
    currentIndex === LAYERS.RADAR.id && onCloseRadar();
    const index = layerValues.value.findIndex((item) => item === currentIndex);
    layerValues.value.splice(index, 1);
  } else {
    layerValues.value.push(currentIndex);
  }
}

function handleTools(type) {
  this[`handleTool${type}`]?.();
}
function handleToolfresh() {
  fresh();
}
async function handleClickLand(id) {
  // console.log(id)
  weatherinfoAnimation.value = !weatherinfoAnimation.value;
  // if (!layerValues.value.includes(2)) {
  //   layerValues.value.push(2);
  // }
  // const weather = await getWeatherGrid({ landId: id });
  // console.log(weather);
  activeLandId.value = id;
}
function handleToollocate() {
  const { map } = map.value;
  map &&
    iplocateAndFit(map).catch(() => {
      proxy.$modal.msgError("定位失败");
    });
}

function handleToolplus() {
  const { map } = map.value;
  map?.zoomIn();
}

function handleToolminus() {
  const { map } = map.value;
  map?.zoomOut();
}
// onMounted(() => {
fresh();
preloadImg(radarInfo.value?.map(({ src }) => src));
// });
</script>
<style scoped lang="scss"></style>
