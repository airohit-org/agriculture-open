<template>
  <div ref="mains" class="area">
    <div ref="mapContainer" class="area-map"></div>
    <!-- <GisMap ref="mapRef" @load="mapLoad" class="area-map" :options="options"></GisMap> -->
  </div>
</template>

<script setup>
import GisMap from "@/components/GisMap/GisMap.vue";
import { UNDEFINED } from "@/constants";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "@geoman-io/leaflet-geoman-free";
import "@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css";
import { getTenantId } from "@/utils/auth";
import { getFarmByTenant, update as updateFarm } from "@/api/farm/farm.js";
import { formatLandCoordinate2Arr } from "@/utils/latlng.js";
import {
  CONFIG,
  createMap,
  createPolygon,
  getStatuslist,
  zoomAndCenterExactArr,
  getStatusFromCache,
} from "../../device/help";
import { getLands, listAll } from "@/api/land/map";
import { findLayerInGroupByParam } from "@/views/device/help.js";
const options = ref();
const hasIcon = ref(false);
const totals = ref(0);
const iconTarget = ref(null);
const farmPosVoList = ref([]);
const arealist = ref([]);
const farmId = ref(UNDEFINED);
const centers = ref([]);
const statuslist = ref([]);
const status = ref([]);
const map = ref(null);
const centerIcon = ref({
  lat: "",
  lng: "",
  save: false,
});
const mapContainer = ref();
const statusMapLayerGroup = ref({});
const { proxy } = getCurrentInstance();
const props = defineProps({
  pos: {
    type: Array,
    default: [],
  },
  posShow: {
    type: Boolean,
  },
});
const emit = defineEmits(["lngAndlat"]);
watch(arealist, (val) => {
  freshLayer();
});
watch(
  () => props.pos,
  (val) => {
    clearLayers();
    //拿到坐标，画图
    const layerGroup = statusMapLayerGroup.value[0];
    framLayerAdd(layerGroup);
    areaLayerList(layerGroup);
    centerFarm();
    if (val[0] != undefined) {
      createIcon(val);
    }
  }
);

async function getFarmInfo() {
  const tenantId = getTenantId();
  getFarmByTenant({ tenantId })
    .then(({ data } = {}) => {
      if (data.coordinateCenter) {
        localStorage.setItem("coordinateCenter", data.coordinateCenter);
      }
      let arr = [];
      let coordinate = JSON.parse(data.coordinate);
      for (let i = 0; i < coordinate.length; i++) {
        let point = [];
        point.push(coordinate[i].latitude);
        point.push(coordinate[i].longitude);
        arr.push(point);
      }
      centers.value = [];
      const polygon = L.polygon(arr);
      centers.value.push(polygon.getBounds().getCenter().lat);
      centers.value.push(polygon.getBounds().getCenter().lng);
      centerFarm();

      const { posVoList, id } = data || {};
      farmPosVoList.value = posVoList || [];
      window.clearFarm = () => updateFarm({ id: farmId.value, posVoList: [] });
      farmId.value = id;
    })
    .catch((res) => {
      console.log(res);
      proxy.$alert("当前功能不可用，请联系管理员", "错误", {
        confirmButtonText: "确定",
        callback: (action) => {
          // $router.push("/");
        },
      });
    });
}
async function getArea() {
  let res = await getLands();
  arealist.value = res.data;
  arealist.value.forEach((ele) => {
    ele.pos = new Array();
    ele.posVoList.forEach((item) => {
      ele.pos.push([item.latitude, item.longitude]);
    });
  });
}
function initLayers() {
  statuslist.value = getStatuslist();
  status.value = getStatusFromCache();
  statuslist.value.forEach(({ value, zIndex }) => {
    if (zIndex) {
      map.value.createPane(`pane-${value}`);
      map.value.getPane(`pane-${value}`).style.zIndex = zIndex;
    }
    const layerGroup = L.layerGroup([]);
    layerGroup.addTo(map.value);
    statusMapLayerGroup.value[value] = layerGroup;
  });
}
// 初始化画图
function initDraw() {
  map.value.pm.setLang("zh");
}
function freshLayer() {
  clearLayers();
  //拿到坐标，画图
  const layerGroup = statusMapLayerGroup.value[0];
  framLayerAdd(layerGroup);
  areaLayerList(layerGroup);
  centerFarm();
  if (props.pos[0] != undefined) {
    createIcon(props.pos);
  }
}
function createIcon(pos) {
  try {
    const layerGroup = statusMapLayerGroup.value[0];
    const iconUrl =
      "https://oss.airoteach.cn/a880153777b87936333b4abf96263a0d493b51852dfe639a929045d853b7fd70.png";
    const icon1 = L.icon({
      iconUrl,
      iconSize: [30, 30],
      iconAnchor: [0, 0],
    });
    const p = L.marker(pos, {
      icon: icon1,
    });
    // p.on("click", (e) => bindChageData(e) );
    iconTarget.value = p;
    layerGroup.addLayer(p);
    hasIcon.value = true;
  } catch (e) {
    console.log("xjq======", e);
  }
}
function centerFarm() {
  const layerGroup = statusMapLayerGroup.value[0];
  const farmBoundaryLayer = findLayerInGroupByParam(
    layerGroup,
    "farmBoundary",
    "_id"
  );
  zoomAndCenterExactArr(
    map.value,
    farmBoundaryLayer?.getLatLngs(),
    [],
    [100, 100]
  );
}
/* 右下角toggle部分 */
// 根据【用户选择】和【后端请求到的地块数据】，渲染图层
//
function bindPopup(e, id) {
  // if(!posShow)return
  if (hasIcon.value) {
    // $confirm('此操作将替换之前选定的位置, 是否继续?', '提示', {
    //   confirmButtonText: '确定',
    //   cancelButtonText: '取消',
    //   type: 'warning'
    // }).then(() => {
    //   $message({
    //     type: 'info',
    //     message: '请先点击之前的坐标取消绑定'
    //   });
    //   return
    Object.values(statusMapLayerGroup.value).forEach((layerGroup) => {
      layerGroup.removeLayer(iconTarget.value);
    });
    const pos = [e.latlng.lat, e.latlng.lng];
    createIcon(pos);
    emit("lngAndlat", e);

    // }).catch(() => {
    //   $message({
    //     type: 'info',
    //     message: '已取消'
    //   });
    // });
  } else {
    const pos = [e.latlng.lat, e.latlng.lng];
    createIcon(pos);
    console.log("xjq===", hasIcon.value);
    emit("lngAndlat", e);
  }
}
function bindChageData(e) {
  console.log(e);
  Object.values(statusMapLayerGroup.value).forEach((layerGroup) => {
    layerGroup.removeLayer(e.target);
  });
  const vals = {
    latlng: {
      lat: "",
      lng: "",
    },
  };
  emit("lngAndlat", vals);
  hasIcon.value = false;
  // layerGroup.removeLayer(p);
}
// 清空所有图层
function clearLayers() {
  Object.values(statusMapLayerGroup.value).forEach((layerGroup) => {
    layerGroup.clearLayers();
  });
}
// 创建地块轮廓图层
function areaLayerList(layerGroup) {
  // arealist = arealist.map(item => [item.latitude, item.longitude]);
  console.log(layerGroup)
  arealist.value.forEach(({ pos, color, id }) => {
    const p = createPolygon(pos, { color }, id, (e) => bindPopup(e, id));
    layerGroup.addLayer(p);
  });
}
function framLayerAdd(layerGroup) {
  try {
    if (!farmPosVoList.value || !farmPosVoList.value?.length) {
      return;
    }
    const pane = map.value.getPane(`pane-${CONFIG.LAYERS.FarmLayer.value}`);
    layerGroup.addLayer(
      createPolygon(
        formatLandCoordinate2Arr(farmPosVoList.value),
        {
          color: "#ffffff",
          pane,
        },
        "farmBoundary",
        (e) => bindPopup(e),
        false
      )
    );
  } catch (err) {
    console.log(err);
  }
}

// beforeDestroy() {
//   clearLayers();
// }

getFarmInfo();
getArea();
proxy.$nextTick(() => {
  map.value = createMap(mapContainer.value);
  initLayers();
  initDraw();
});
</script>

<style scoped lang="scss">
.area {
  position: relative;

  &-map {
    height: calc(100vh - 196px);
    position: relative;
  }
}
</style>
