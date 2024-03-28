<template>
  <div class="map" ref="mapRef">
    <div v-if="!hiddenInfo" class="info">
      <span>面积：{{ fids[active]?.area }}</span>
      <span>作物类型：{{ cropTypeMap[fids[active]?.crop_type] }}</span>
    </div>
    <div v-if="!hiddenInfo" class="growth-map-selected">
      <el-radio-group v-model="radioLeft">
        <span class="growth-map-selected-title">左侧:</span>
        <el-radio v-for="item in list" :label="item">{{ item }}</el-radio>
      </el-radio-group>
      <el-radio-group v-model="radioRight">
        <span class="growth-map-selected-title">右侧:</span>
        <el-radio v-for="item in list" :label="item">{{ item }}</el-radio>
      </el-radio-group>
    </div>
    <img
      v-if="!hiddenInfo"
      class="legend"
      src="https://oss.airoteach.cn/945a85c63ae990a3773afc975a4df324a0ed6d16a48ea0b31ce8ebe6f8039674.png"
      alt=""
    />
    <div class="whole-modal" v-if="loading">
      <BLoading />
    </div>

    <div class="whole-modal" v-if="feedBack">
      <FeedBack value="当前农场暂无长势分析数据，请联系管理员" />
    </div>
  </div>
</template>

<script setup>
import {
  createMap,
  zoomAndCenterExactArr,
  CONFIG,
  createSide,
  drawOnClick,
  createPolygon,
  addOneGrowthLayer,
} from "./help";
import FeedBack from "./feedBack.vue";
import BLoading from "@/components/BLoading/index.vue";
import "leaflet/dist/leaflet.css";
import useBaseMapStore from "@/store/modules/baseMap";
const baseMapStore = useBaseMapStore();
const cropTypeMap = ["未耕种/未知", "水稻", "玉米", "大豆"];
const fids = ref({});
const active = ref(null);
const landLayer = ref(null);
const growthLayer = ref(null);
const radioLeft = ref("");
const feedBack = ref("");
const radioRight = ref("");
const loading = ref(false);
const map = ref();
const mapRef = ref();
const side = ref();
const leftLayer = ref();
const rightLayer = ref();
const { farm, growthAnalysis, landSegUrl } = toRefs(baseMapStore);
const list = computed(() => {
  return Object.keys(growthAnalysis.value || {});
});
const props = defineProps({
  hiddenInfo: {
    type: Boolean,
  },
});

watch(
  list,
  (val) => {
    const [one, two = one] = val
    if (one && two) {
      radioLeft.value = one;
      radioRight.value = two;
    }
  }
);
watch(radioLeft, () => {
  freshLayer();
});
watch(radioRight, () => {
  freshLayer();
});

function fresh() {
  loading.value = true;
  baseMapStore
    .getMapInfo()
    .then(() => {
      zoomAndCenterExactArr(map.value, farm.value, [], [0, 0]);
      loading.value = false;
      if (!growthAnalysis.value) {
        feedBack.value = true;
        return;
      }
      bindClick();
      freshLayer();
    })
    .finally(() => {
      loading.value = false;
    });
}
function initMap() {
  map.value = createMap(mapRef.value);
}
function freshLayer() {
  if (!growthAnalysis.value) {
    feedBack.value = true;
    return;
  }
  if (side.value) {
    map.value.removeControl(side.value);
    side.value = null;
  }
  if (leftLayer.value) {
    map.value.removeLayer(leftLayer.value);
    leftLayer.value = null;
  }
  if (rightLayer.value) {
    map.value.removeLayer(rightLayer.value);
    rightLayer.value = null;
  }
  initLayer();
}
function initLayer() {
  if (!(growthAnalysis.value || radioLeft.value || radioRight.value)) {
    return;
  }
  leftLayer.value = addOneGrowthLayer(
    map.value,
    growthAnalysis.value[radioLeft.value]
  );
  rightLayer.value = addOneGrowthLayer(
    map.value,
    growthAnalysis.value[radioRight.value]
  );
  side.value = createSide(map.value, leftLayer.value, rightLayer.value);
}

function bindClick() {
  drawOnClick(
    map.value,
    landSegUrl.value,
    (pos, { FID, area, crop_type, scores } = {}) => {
      const { layer: _layer } = fids[FID] || {};
      if (_layer) {
        map.value.removeLayer(_layer);
        fids.value[FID].layer = null;
        active.value = null;
      } else {
        active.value = FID;
        const layer = createPolygon(pos);
        map.value.addLayer(layer);
        layer.getPane().style.zIndex = 401;
        fids.value[FID] = { area, crop_type, scores, layer };
      }
    }
  );
}

onMounted(() => {
  initMap();
  fresh();
});
</script>

<style scoped lang="scss">
.map {
  width: 100%;
  height: 100%;
}

.info {
  position: absolute;
  left: 0;
  top: 0;
  z-index: 401;
  display: flex;
  flex-direction: column;
  background-color: #000000cc;

  span {
    padding: 15px 20px;
    color: #fff;
  }
}

.growth-map-selected {
  z-index: 500;
  position: absolute;
  left: 0px;
  height: 100px;
  bottom: 0px;
  display: flex;
  flex-direction: column;
  background-color: #000000cc;
  align-items: center;
  justify-content: space-between;
  padding: 20px;

  &-title {
    color: #fff;
    font-size: 14px;
    margin: 0 14px;
  }
}

.legend {
  position: absolute;
  z-index: 500;
  right: 0;
  bottom: 0;
  width: 100px;
}

.whole-modal {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 503;
}
</style>
