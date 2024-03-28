<template>
  <div class="segmentation">
    <div class="map" ref="mapRef">
      <div class="info">
        <span>面积：{{ fids[active]?.area }}</span>
        <span>作物类型：{{ cropTypeMap[fids[active]?.crop_type] }}</span>
        <!-- <span>分数：{{ fids[active]?.scores }}</span> -->
      </div>
    </div>
    <div class="whole-modal" v-if="loading">
      <BLoading />
    </div>

    <div class="whole-modal" v-if="feedBack">
      <FeedBack value="当前农场暂无地块分割数据，请联系管理员" />
    </div>
  </div>
</template>

<script setup>
import {
  createMap,
  addLandLayer,
  zoomAndCenterExactArr,
  CONFIG,
  drawOnClick,
  createPolygon,
  getLatlngByPopup,
} from "./help";
import "leaflet/dist/leaflet.css";
import FeedBack from "./feedBack.vue";
import BLoading from "@/components/BLoading/index.vue";
import useBaseMapStore from "@/store/modules/baseMap";
const baseMapStore = useBaseMapStore();
const cropTypeMap = ["未耕种/未知", "水稻", "玉米", "大豆"];
const fids = ref({});
const mapRef = ref();
const map = ref();
const active = ref(null);
const landLayer = ref(null);
const growthLayer = ref(null);
const feedBack = ref(false);
const loading = ref(false);
const { farm, landSegUrl } = toRefs(baseMapStore);
function fresh() {
  loading.value = true;
  baseMapStore
    .getMapInfo()
    .then(() => {
      zoomAndCenterExactArr(map.value, farm.value, [], [0, 0]);
      loading.value = false;
      if (!landSegUrl.value) {
        feedBack.value = true;
        return;
      }
      bindClick();
      initLayer();
    })
    .finally(() => {
      loading.value = false;
    });
}

function initMap() {
  map.value = createMap(mapRef.value);
}
function initLayer() {
  landLayer.value = addLandLayer(map.value, landSegUrl.value);
}
function bindClick() {
  drawOnClick(
    map.value,
    landSegUrl.value,
    (pos, { FID, area, crop_type, scores } = {}) => {
      const { layer: _layer } = fids[FID] || {};
      if (!(landLayer.value || growthLayer.value)) {
        return;
      }
      if (_layer) {
        map.value.removeLayer(_layer);
        fids.value[FID].layer = null;
        active.value = null;
      } else {
        active.value = FID;
        const layer = createPolygon(pos);
        map.value.addLayer(layer);
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
.segmentation {
  width: 100%;
  height: calc(100vh - 96px);
}

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
  background-color: #333333cc;

  span {
    padding: 15px 20px;
    color: #fff;
  }
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
