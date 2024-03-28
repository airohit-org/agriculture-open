<template>
  <div ref="mains" class="area">
    <MapLight :zIndex="405" />
    <div class="deviceTotal">
      <div class="icon">
        <span class="con"></span>
      </div>
      <div class="text">
        <span
          style="
            display: inline-block;
            margin-top: 2px;
            color: #fff;
            font-size: 18px;
            margin-left: 5px;
          "
          >总数</span
        >
        <span
          style="
            display: inline-block;
            margin-top: 2px;
            color: #fcf366;
            font-size: 18px;
            margin-left: 5px;
          "
          >{{ totals }}</span
        >
      </div>
    </div>
    <!-- <div class="showing" v-if="deviceList>0"> -->
    <div class="showing">
      <div class="item" v-for="item in deviceList" :key="item.deviceId">
        <div>
          <img class="img" :src="item.imgUrl" alt="" />
        </div>
        <div style="display: flex; flex-direction: column">
          <div class="con" style="color: #fff; font-size: 12px">
            {{ item.deviceName }}
          </div>
          <div class="con count" style="color: #1fface; font-size: 22px">
            {{ item.count
            }}<span
              style="
                display: inline-block;
                font-size: 12px;
                color: #fff;
                margin-left: 5px;
              "
              >个</span
            >
          </div>
        </div>
      </div>
    </div>
    <div class="bottomSelectLayer">
      <LayerSelect :list="statuslist" v-model="status" />
    </div>
    <div v-show="loading" class="BLoading">
      <BLoading />
    </div>
    <!-- 地块弹窗 -->
    <div class="popup-container" ref="popup" v-show="popupShow">
      <!-- <img class="popup-container-topBorder"
        src="https://oss.airoteach.cn/9a8e5334fb73ee89c95f8c3944949d75c9d2d98ec36a9d6df8b6d52e84551b3e.png" /> -->
      <MapPopup
        :title="arealistMap?.[current]?.name"
        :info="arealistMap[current]"
        :popupShow="popupShow"
      />
      <!-- <img class="popup-container-bottomBorder"
        src="https://oss.airoteach.cn/4aaf6ace8e56d96655f34b1831d967be42aabcf6e6118d3f559162c647497dfe.png" /> -->
    </div>
    <div class="weatherList">
      <OverviewTitle style="width: 330px" :title="exhibitTitle" />
      <exhibit :list="WEATHER_LIST"></exhibit>
    </div>
    <div class="deviceList">
      <OverviewTitle style="width: 330px" title="设备监测" />
      <leftDevice @deviceItemInfo="deviceItemInfo" :list="device"></leftDevice>
    </div>

    <div ref="mapRef" class="area-map"></div>
  </div>
</template>

<script setup>
import { deviceType, getRealTimeData } from "@/api/obs/deviceInfo";
import { getUserDeviceList } from "@/api/obs/deviceInfo";
import {
  getDeviceGroupVoList,
  getDeviceLandListVoList,
  getSoilDeviceDataDONew,
  getWeatherDeviceDataDONew,
} from "@/api/device/info.js";
import { getFarmByTenant, update as updateFarm } from "@/api/farm/farm.js";
import BLoading from "@/components/BLoading/index.vue";
import MapLight from "@/components/MapLight/index.vue";
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import exhibit from "@/components/exhibit/index.vue";
import { STORE_KEY, getStore } from "@/utils";
import { getTenantId } from "@/utils/auth";
import { formatLandCoordinate2Arr } from "@/utils/latlng.js";
import { getPath } from "@/utils/ruoyi";
import { findLayerInGroupByParam } from "@/views/device/help.js";
import "@geoman-io/leaflet-geoman-free";
import "@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
// import { createNamespacedHelpers, mapGetters } from "vuex";
import useLandMapStore from "@/store/modules/landMap";
import LayerSelect from "./buComponents/LayerSelect.vue";
import MapPopup from "./buComponents/MapPopup.vue";
import leftDevice from "./buComponents/leftDevice.vue";
import {
  COLOR_LIST,
  CONFIG,
  TOP_BTN_SHOW_ENUM,
  createMap,
  createPolygon,
  formatSearchOptions,
  getStatusFromCache,
  getStatuslist,
  keyTitle,
  setStatusStore,
  zoomAndCenterExactArr,
} from "./help";
import useUserStore from "@/store/modules/user";
const userStore = useUserStore();
const { proxy } = getCurrentInstance();
const deviceTypeList = ref([]);
const totals = ref(0);
const exhibitTitle = ref("气象站");
const device = ref([]);
const deviceList = ref([]);
const WEATHER_LIST = ref([]);
const loading = ref(false);
const popup = ref();
//  const   COLOR_LIST)
const topBtnShow = ref(TOP_BTN_SHOW_ENUM.NO_DRAWING);
const form = ref({});
const editPrev = ref([]);
const editAfter = ref([]);
const statusMapLayerGroup = ref({});
// 画地块临时图层
const drawLayer = ref(null);
// 画地块弹框标题
const title = ref("添加地块");
// 计划名称数组
const planNameArray = ref([]);
const disabled = ref(false);
// 表单校验
const rules = ref({});
// 表单
const open = ref(false);
// 初始化不展示弹窗
const popupShow = ref(false);
// 当前被点击的地块id
const current = ref(-1);
// 搜索文案
const serachValue = ref("");
// 展示哪些图层备选
const statuslist = ref([]);
// 展示哪些图层
const status = ref([]);
// 正在画
const drawing = ref(false);
const init = ref(false);
const hover = ref(false);
const farmPosVoList = ref([]);
const farmId = ref(localStorage.getItem("farm-tenant-id") || undefined);
const centers = ref([]);
const IsSingle = ref(false);
const deviceItemId = ref(1);
const mapRef = ref();
const map = ref();
const landMapStore = useLandMapStore();

const {
  total,
  raiseCrops,
  raiseCropsMap,
  landPage,
  searchOptions,
  arealistMap,
  arealist,
  radarInfo,
} = toRefs(landMapStore);

watch(arealist, () => {
  freshLayer();
});
watch(status, () => {
  clearLayers();
  centerFarm();
  setStatusStore(status.value);
  if (getTenantId() != 166) {
    // clearLayers();
    const layerGroup = statusMapLayerGroup.value[0];
    areaLayerList(layerGroup);
    framLayerAdd(layerGroup);
    createDeviceIconLayer(layerGroup);
    IsSingle.value = false;
  } else {
    freshLayer();
    IsSingle.value = false;
  }
});

watch(farmPosVoList, () => {
  freshLayer();
});

/**
 * 获取设备类型
 */
async function deviceTypeGet() {
  let res = await deviceType();
  deviceTypeList.value = res.data;
}
/**
 * 设备数据&& 统计
 */
async function deviceGroupVoListGet() {
  let res = await getDeviceGroupVoList();
  deviceList.value = [];
  deviceList.value = res.data;
  let sum = 0;
  for (let i = 0; i < deviceList.value?.length; i++) {
    switch (deviceList.value[i].deviceName) {
      case "气象站":
        deviceList.value[i].imgUrl =
          "https://oss.airoteach.cn/3a3119d73c6749eabac747fd0acc312b1847ae895ad17bdc7135ddcb81d96444.png";
        break;
      case "虫情监测站":
        deviceList.value[i].imgUrl =
          "https://oss.airoteach.cn/f11a4658b25cba954a68bc80f162173a003f15acff4d6318aee05a288b323a81.png";
        break;
      case "智慧环控设备":
        deviceList.value[i].imgUrl =
          "https://oss.airoteach.cn/676d72204279db8490d04a953ed262f636e7d6246b7095f88f1d3881c6f316e7.png";
        break;
      case "病情监测站":
        deviceList.value[i].imgUrl =
          "https://oss.airoteach.cn/7d6fdb03e8f24194bcc4fddbe2c10a541bcfb3a175118980d3bc7eb099decc7e.png";
        break;
      case "土壤墒情站":
        deviceList.value[i].imgUrl =
          "https://oss.airoteach.cn/4ede79314a5f5193682142b1e39fc04f8433feeef1153ba462d131dcf12b239c.png";
        break;
    }
    sum += deviceList.value[i].count;
  }
  deviceList.value = deviceList.value.reduce((acc, curr) => {
    const existingItem = acc.find(
      (item) => item.deviceName === curr.deviceName
    );
    if (existingItem) {
      existingItem.count += curr.count;
    } else {
      acc.push(curr);
    }
    return acc;
  }, []);
  totals.value = sum;
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
/**
 * 获取左侧设备列表
 */
async function userDeviceListGet() {
  let res = await getUserDeviceList({ farmId: farmId.value });
  let arr = [];
  arr = res.data;
  arr = arr.filter(
    (ele) => (ele.devicelat && ele.devicelng) || ele.landId != null
  );
  arr?.forEach((ele) => {
    ele.state = 1;
    for (let i = 0; i < deviceTypeList.value.length; i++) {
      if (ele.deviceType == deviceTypeList.value[i].value) {
        ele.deviceTypeName = deviceTypeList.value[i].label;
        ele.imgUrl = deviceTypeList.value[i].remark;
        ele.mapIconUrl = deviceTypeList.value[i].cssClass;
        return;
      }
    }
  });
  if (arr.length != 0) {
    await weatherDeviceDataDONewGet();
  }
  device.value = arr;
}
/**
 * 绿农数据
 */
async function deviceLandListVoListGet() {
  let res = await getDeviceLandListVoList();
  device.value = [];
  device.value = res.data.filter(
    (ele) => ele.deviceId == 52 || ele.deviceId == 51
  );
  for (let i = 0; i < device.value?.length; i++) {
    if (device.value[i].deviceId == 52) {
      device.value[i].state = 1;
      device.value[i].imgUrl =
        "https://oss.airoteach.cn/ee8ca6410e40b83975521b3acd97ef197ed5a0c7570377bb92c53c60a7130289.png";
    } else if (device.value[i].deviceId == 51) {
      device.value[i].state = 1;
      device.value[i].imgUrl =
        "https://oss.airoteach.cn/b862a0e66b95dedf810af8e6403d02af93598a7e45ed6ac07278f2f1e9dc153f.png";
    }
  }
}

async function soilDeviceDataDONewGet() {
  let res = await getSoilDeviceDataDONew();
  const { data } = res;
  const arr = [];
  for (const key in data) {
    if (keyTitle[key]?.split("|")[0] != undefined) {
      arr.push({
        imgSrc: keyTitle[key].split("|")[2],
        title: keyTitle[key].split("|")[0],
        prompt: data[key] + "" + keyTitle[key].split("|")[1],
      });
    }
  }
  WEATHER_LIST.value = arr;
}
async function weatherDeviceDataDONewGet() {
  let res = await getWeatherDeviceDataDONew();
  const { data } = res;
  const arr = [];
  for (const key in data) {
    if (keyTitle[key]?.split("|")[0] != undefined) {
      arr.push({
        imgSrc: keyTitle[key].split("|")[2],
        title: keyTitle[key].split("|")[0],
        prompt:
          (data[key] ? data[key] : "0") + "" + keyTitle[key].split("|")[1],
      });
    }
  }
  WEATHER_LIST.value = arr;
}
function deviceItemInfo(item) {
  IsSingle.value = true;
  status.value = [];
  exhibitTitle.value = item.deviceName;
  deviceItemId.value = item.id;
  if (getTenantId() != 166) {
    exhibitTitle.value = item.deviceTypeName;
    switch (item.deviceTypeName) {
      case "气象站":
        if (item.state != 0) {
          status.value.push(1);
        }
        break;
      case "土壤墒情监测站":
        if (item.state != 0) {
          status.value.push(2);
        }
        break;
      case "病情监测站":
        break;
      case "智慧环控设备":
        if (item.state != 0) {
          status.value.push(3);
        }
        break;
      case "虫情监测站":
        if (item.state != 0) {
          status.value.push(4);
        }
        break;
    }
  } else {
    // deviceItemId = item.id
    //气象1，土壤2，苗情0，虫情3，杀虫4
    if (item.deviceName == "苗情监测站") {
      status.value.push(0);
    } else if (item.deviceName == "虫情监测站") {
      status.value.push(3);
    } else if (item.deviceName == "杀虫灯") {
      status.value.push(4);
    } else if (item.deviceName == "气象站" && item.state != 0) {
      status.value.push(1);
    } else if (item.deviceName == "土壤墒情监测站" && item.state != 0) {
      status.value.push(2);
    }
  }
  bindChageData("", item.id);
}

function getFarmInfo() {
  const tenantId = getTenantId();
  if (!tenantId) {
    proxy.$modal.confirm("请重新登录", "提示").then(() => {
      proxy.$store.dispatch("LogOut").then(() => {
        location.href = getPath("/index");
      });
    });
    return;
  }
  getFarmByTenant({ tenantId })
    .then(({ data } = {}) => {
      if (data.coordinateCenter) {
        localStorage.setItem("coordinateCenter", data.coordinateCenter);
      }
      let arr = [];
      let coordinate = JSON.parse(data.coordinate);
      for (let i = 0; i < coordinate?.length; i++) {
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
      topBtnShow.value = farmPosVoList.value.length
        ? TOP_BTN_SHOW_ENUM.NO_DRAWING
        : TOP_BTN_SHOW_ENUM.FARM;
      window.clearFarm = () => updateFarm({ id: farmId.value, posVoList: [] });
      farmId.value = id;
    })
    .catch((res) => {
      console.log("catch", res);
      // $alert("当前功能不可用，请联系管理员", "错误", {
      //   confirmButtonText: "确定",
      //   callback: (action) => {
      //     $router.push("/");
      //   },
      // });
    });
}

function handleToolfresh() {
  landMapStore.loadAreaList();
}

function handleSearch(landName) {
  loading.value = true;
  landMapStore
    .loadPageLandSearch({ landName })
    .then(() => {
      loading.value = false;
    })
    .catch(() => {
      loading.value = false;
    });
}

// 初始化画图
function initDraw() {
  map.value.pm.setLang("zh");
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

/* 右下角toggle部分 */
// 根据【用户选择】和【后端请求到的地块数据】，渲染图层
function freshLayer() {
  clearLayers();
  const layerGroup = statusMapLayerGroup.value[0];
  areaLayerList(layerGroup);
  framLayerAdd(layerGroup);
  status.value.forEach((item) => {
    const layerGroup = statusMapLayerGroup.value[item];
    ({
      [CONFIG.LAYERS.AreaLayer.value]: createAreaLayer,
      [CONFIG.LAYERS.IconLayer.value]: createIconLayer,
      [CONFIG.LAYERS.TextLayer.value]: createTextLayer,
      [CONFIG.LAYERS.FarmLayer.value]: createFarmLayer,
      [CONFIG.LAYERS.DeviceLayer.value]: createDeviceLayer,
    })?.[item]?.(layerGroup);
  });
  // 首次创建成功后，自动聚焦
}
// 清空所有图层
function clearLayers() {
  Object.values(statusMapLayerGroup.value).forEach((layerGroup) => {
    layerGroup.clearLayers();
  });
}

function bindPopup({ latlng: { lat, lng } = {} }, id) {
  current.value = id;
  popupShow.value = true;
  const popup = L.popup();
  popup.setLatLng({ lat, lng }).setContent(popup.value).openOn(map.value);
}
/**
 * 坐标点击事件
 * @param {*} e
 * @param {*} id
 */
async function bindChageData(e, id) {
  WEATHER_LIST.value = [];
  if (getTenantId() != 166) {
    const arr = device.value.filter((ele) => ele.id == id);
    const param = {
      pageNo: 1,
      pageSize: 1,
      deviceId: arr[0]?.id,
    };
    getRealTimeData(param).then((res) => {
      let arr = [];
      res.data.list[0]?.data.forEach((ele) => {
        arr.push({
          imgSrc:
            "https://oss.airoteach.cn/e849176092a7783f168af6238c10c038c649830ce1a11b49998ec1d69719f5cd.png",
          title: ele.name,
          prompt: ele.value + "" + ele.unit,
        });
      });
      WEATHER_LIST.value = arr;
    });
  } else {
    const arr = device.value.filter((ele) => ele.id == id);
    if (arr.length > 0) {
      if (arr[0].deviceName == "气象站") {
        await weatherDeviceDataDONewGet();
      } else if (arr[0].deviceName == "土壤墒情监测站") {
        soilDeviceDataDONewGet();
      }
    }
  }
}
// 创建轮廓图层
function createAreaLayer(layerGroup) {
  if (IsSingle.value) {
    const index = device.value.findIndex((ele) => ele.id == deviceItemId.value);
    map.panTo([
      device.value[index]?.coordinate?.split(",")[0],
      device.value[index]?.coordinate?.split(",")[1],
    ]);
    const pos = [
      device.value[index].coordinate?.split(",")[0],
      device.value[index].coordinate?.split(",")[1],
    ];
    const iconUrl =
      "https://oss.airoteach.cn/498072fdbd98a576c02cf1310c14ea3d20e1f844e06585213de14241a4b0342c.png";
    const icon1 = L.icon({
      iconUrl,
      iconSize: [50, 50],
      iconAnchor: [22, 0],
    });
    const p = L.marker(pos, {
      icon: icon1,
    });
    p.on("click", (e) => bindChageData(e, deviceItemId.value));
    layerGroup.addLayer(p);
    IsSingle.value = false;
  } else {
    centerFarm();
    let pointList = [];
    pointList = device.value.filter((ele) => ele.deviceId == 52);
    pointList?.forEach((item) => {
      const pos = [
        item.coordinate.split(",")[0],
        item.coordinate.split(",")[1],
      ];
      const iconUrl =
        "https://oss.airoteach.cn/498072fdbd98a576c02cf1310c14ea3d20e1f844e06585213de14241a4b0342c.png";
      const icon1 = L.icon({
        iconUrl,
        iconSize: [40, 40],
        iconAnchor: [22, 0],
      });
      const p = L.marker(pos, {
        icon: icon1,
      });
      p.on("click", (e) => bindChageData(e, item.id));
      layerGroup.addLayer(p);
    });
  }
}
function areaLayerList(layerGroup) {
  arealist.value.forEach(({ pos, color, id }) => {
    const p = createPolygon(pos, { color }, id, (e) => bindPopup(e, id));
    layerGroup.addLayer(p);
  });
}
function framLayerAdd(layerGroup) {
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
        fillOpacity: 0.1,
      },
      "farmBoundary",
      false,
      false
    )
  );
}

function createIconLayer2(layerGroup) {
  // arealist
  //   .filter(({ center }) => center)
  //   .forEach(({ center: pos, cropsCreateReqVOList, id }) => {
  //     const allCrops = [
  //       ...new Set(cropsCreateReqVOList.map(({ crops }) => crops)),
  //     ];
  //     allCrops.forEach((crops, index) => {
  //       const iconUrl =
  //         "https://oss.airoteach.cn/45d59857cbef760dffe6fda0d548dea2964411f7aa62063d2f854e1c3874ee07.png";
  //       const icon1 = L.icon({
  //         iconUrl,
  //         iconSize: [40, 40],
  //         iconAnchor: [0, 22],
  //       })
  //       const p = L.marker(pos, {
  //         icon: icon1,
  //       });
  //       p.on("click", (e) => bindPopup(e, id));
  //       layerGroup.addLayer(p);
  //     });
  //   });
}
// 创建icon图层
function createIconLayer(layerGroup) {
  if (IsSingle.value) {
    const index = device.value.findIndex((ele) => ele.id == deviceItemId.value);
    map.value.panTo([
      device.value[index].coordinate.split(",")[0],
      device.value[index].coordinate.split(",")[1],
    ]);
    const pos = [
      device.value[index].coordinate.split(",")[0],
      device.value[index].coordinate.split(",")[1],
    ];
    const iconUrl =
      "https://oss.airoteach.cn/f3ff158dceddc120ce57a26672179d4eb2e758759c189e6daa6090278a081421.png";
    const icon1 = L.icon({
      iconUrl,
      iconSize: [50, 50],
      iconAnchor: [0, 0],
    });
    const p = L.marker(pos, {
      icon: icon1,
    });
    p.on("click", (e) => bindChageData(e, deviceItemId.value));
    layerGroup.addLayer(p);
    IsSingle.value = false;
  } else {
    centerFarm();
    let pointList = [];
    pointList = device.value.filter((ele) => ele.deviceId == 52);
    pointList?.forEach((item) => {
      const pos = [
        item.coordinate.split(",")[0],
        item.coordinate.split(",")[1],
      ];
      const iconUrl =
        "https://oss.airoteach.cn/f3ff158dceddc120ce57a26672179d4eb2e758759c189e6daa6090278a081421.png";
      const icon1 = L.icon({
        iconUrl,
        iconSize: [40, 40],
        iconAnchor: [0, 0],
      });
      const p = L.marker(pos, {
        icon: icon1,
      });
      p.on("click", (e) => bindChageData(e, item.id));
      layerGroup.addLayer(p);
    });
  }
}
// 创建createDeviceLayer
function createDeviceLayer(layerGroup) {}

/**
 * 传入图层将设备图标盖到图层上，绑定点击事件
 * @param {Object} layerGroup
 */
async function createDeviceIconLayer(layerGroup) {
  if (IsSingle.value) {
    const index = device.value.findIndex((ele) => ele.id == deviceItemId.value);
    let pos = [];
    if (device.value[index]?.devicelat && device.value[index]?.devicelng) {
      map.value.panTo([
        device.value[index].devicelat,
        device.value[index].devicelng,
      ]);
      pos = [device.value[index].devicelat, device.value[index].devicelng];
    } else {
      if (device.value[index]?.landId != null) {
        const arr = arealist.value.filter(
          (ele) => ele.id == device.value[index].landId
        );
        map.value.panTo([
          arr[0].landCoordinateCenter.split(",")[0],
          arr[0].landCoordinateCenter.split(",")[1],
        ]);
        pos = [
          arr[0].landCoordinateCenter.split(",")[0],
          arr[0].landCoordinateCenter.split(",")[1],
        ];
      }
    }
    const iconUrl = device.value[index].mapIconUrl;
    const icon1 = L.icon({
      iconUrl,
      iconSize: [40, 40],
      iconAnchor: [0, 0],
    });
    const p = L.marker(pos, {
      icon: icon1,
    });
    p.on("click", (e) => bindChageData(e, deviceItemId.value));
    layerGroup.addLayer(p);
    IsSingle.value = false;
  } else {
    centerFarm();
    let pointList = [];
    const deviceMap = [
      "病情监测站",
      "气象站",
      "土壤墒情站",
      "智慧环控设备",
      "虫情监测站",
      "视频监控",
    ];
    const arr = [];
    status.value.forEach((ele) => {
      arr.push(deviceMap[ele]);
    });
    pointList = device.value.filter((item) =>
      arr.includes(item.deviceTypeName)
    );
    pointList?.forEach((item, index) => {
      let pos = [];
      if (pointList[index]?.devicelat && pointList[index]?.devicelng) {
        pos = [pointList[index].devicelat, pointList[index].devicelng];
      } else {
        const arr = arealist.value.filter(
          (ele) => ele.id == device.value[index].landId
        );
        pos = [
          arr[0].landCoordinateCenter.split(",")[0],
          arr[0].landCoordinateCenter.split(",")[1],
        ];
      }
      const iconUrl = item.mapIconUrl;
      const icon1 = L.icon({
        iconUrl,
        iconSize: [40, 40],
        iconAnchor: [0, 0],
      });
      const p = L.marker(pos, {
        icon: icon1,
      });
      p.on("click", (e) => bindChageData(e, item.id));
      layerGroup.addLayer(p);
    });
  }
}
// 创建农场边界图层
function createFarmLayer(layerGroup) {
  createIconLayer2(layerGroup);
}

// 创建文字图层
function createTextLayer(layerGroup) {}

function reset() {
  const { mobile } = getStore(STORE_KEY.USER_INFO);
  form.value = {
    color: COLOR_LIST[0],
    id: undefined,
    landName: undefined,
    cropsCreateReqVOList: [
      {
        crops: raiseCrops.value[0]?.code,
        cropsIsOther: 0,
        cropsTypeIsOther: 0,
        cropsType: raiseCrops.value[0]?.cropsVarietiesList?.[0]?.code,
        cropsOtherContent: "",
        cropsTypeOtherContent: "",
      },
    ],
    area: undefined,
    contacts: userStore.nickname,
    tel: mobile,
    landCoordinate: undefined,
    landCoordinateCenter: undefined,
  };
}

onMounted(() => {
  getFarmInfo();
  deviceTypeGet();
  landMapStore.loadRaiseCrops().then(() => {
    landMapStore.loadAreaList();
    reset();
    deviceGroupVoListGet();
    if (getTenantId() != 166) {
      userDeviceListGet();
    } else {
      deviceLandListVoListGet();
    }
  });
  handleSearch();
  proxy.$nextTick(async () => {
    map.value = createMap(mapRef.value);
    initLayers();
    initDraw();
    if (getTenantId() == 166) {
      await weatherDeviceDataDONewGet();
    }
  });
  init.value = true;
});
// const searchOptions = computed(() => {
//   return formatSearchOptions(raiseCropsMap, form.crops);
</script>

<style scoped lang="scss">
@import "./styles/popup.scss";

:deep(.bg .icon-bg .icon) {
  width: 42px;
  height: 42px;
}

.area {
  position: relative;

  &-map {
    height: calc(100vh - 96px);
    position: relative;
  }

  .deviceTotal {
    position: absolute;
    top: 25px;
    transform: translate(-50%);
    left: 50%;
    z-index: 999;
    width: 426px;
    height: 31px;
    background: linear-gradient(
      270deg,
      rgba(3, 251, 255, 0) 0%,
      rgba(5, 252, 255, 0.58) 40%,
      rgba(6, 252, 255, 0.6) 43%,
      rgba(6, 253, 255, 0.61) 47%,
      rgba(7, 253, 255, 0) 100%
    );
    display: flex;

    .icon {
      width: 16px;
      height: 16px;
      background: rgba(31, 250, 206, 0.2);
      border-radius: 0px 0px 0px 0px;
      border: 1px solid rgba(31, 250, 206, 0.5);
      text-align: center;
      margin-left: 160px;
      margin-top: 6px;

      .con {
        display: flex;
        margin-left: 3px;
        margin-top: 3px;
        width: 8px;
        height: 8px;
        background: #1fface;
      }

      .text {
        width: 36px;
        font-size: 18px;
        font-family: PingFang SC-Regular, PingFang SC;
        font-weight: 400;
      }
    }
  }

  .BLoading {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    display: grid;
    place-items: center;
    background-color: rgba($color: #000000cc, $alpha: 0.5);
    z-index: 9999;

    :deep(.bloading) {
      background-color: rgba($color: #000000, $alpha: 0.1);
    }
  }

  .showing {
    position: absolute;
    top: 58px;
    transform: translate(-50%);
    left: 50%;
    z-index: 999;
    // width: 47%;
    height: 114px;
    display: flex;
    padding-left: 35px;
    padding-right: 25px;
    // flex-wrap: nowrap;
    background-image: url("https://oss.airoteach.cn/a37f3291df93df4b10bd67b510e71cf318e307be3743fb7356ac4d130c7baf38.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;

    .item {
      display: flex;
      margin-left: 30px;

      .img {
        display: inline-block;
        margin-top: 36px;
        width: 40px;
        height: 40px;
      }

      .con {
        display: inline-block;
        margin-top: 35px;
        margin-left: 4px;
      }

      .count {
        margin-top: -1px;
        text-align: left;
      }
    }

    .item:first-child {
      margin-left: 0;
    }

    .item:last-child {
      margin-right: 10px;
    }
  }

  .showing::before {
    content: "";
    position: relative;
    top: 45px;
    left: -0.5vw;
    z-index: 999;
    height: 0px;
    width: 0px;
    border-left: 10px solid #ffffff;
    border-top: 10px solid transparent;
    border-bottom: 10px solid transparent;
  }

  .showing::after {
    content: "";
    position: relative;
    top: 45px;
    right: 0;
    z-index: 999;
    height: 0px;
    width: 0px;
    border-right: 10px solid #ffffff;
    border-top: 10px solid transparent;
    border-bottom: 10px solid transparent;
  }

  .bottomSelectLayer {
    position: absolute;
    bottom: 0;
    transform: translate(-50%);
    left: 50%;
    z-index: 999;
    // width: 37%;
  }

  .weatherList {
    position: absolute;
    width: 330px;
    height: 72vh;
    right: 20px;
    top: 25px;
    z-index: 999;

    :deep(.bg) {
      width: 90%;
    }
  }

  .deviceList {
    position: absolute;
    width: 330px;
    height: 70vh;
    left: 22px;
    top: 25px;
    z-index: 999;
  }
}

.search {
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 999;
}

:deep(.my-div-icon) {
  border: 1px solid #ffffff22;
  white-space: nowrap;
  padding: 10px 15px;
  border-radius: 5px;
  width: auto !important;
  height: auto !important;
  background: #0a2832;
  color: #fff;
}

.crop-select {
  display: flex;
  flex-wrap: wrap;

  &-item {
    cursor: pointer;
    // width: 36px;
    height: 36px;
    aspect-ratio: 1 / 1;
    margin-right: 10px;
    border: 2px solid #eee;
    transition: all ease 0.2s;
    padding: 4px;
    display: grid;
    place-items: center;

    &-active {
      border-color: #11a983;
      border-width: 4px;
      padding: 4px;
      border-radius: 50%;
    }

    img {
      width: 100%;
      height: 100%;
    }
  }
}

.crop-createlist {
  // background: red;
  display: flex;
  flex-direction: column;

  &-item {
    width: 75%;
    display: grid;
    grid-template-columns: 400px 40px;

    :deep(.el-form-item__content) {
      margin-left: 0px !important;
    }

    &-info {
      display: flex;
      flex-wrap: nowrap;
    }

    &-btn {
      display: flex;
      flex-wrap: nowrap;
    }
  }
}

.color-select {
  display: flex;
  flex-wrap: nowrap;

  &-item {
    cursor: pointer;
    height: 36px;
    aspect-ratio: 1 / 1;
    margin-right: 10px;
    border: 2px solid #eee;
    background-clip: content-box;
    transition: all ease 0.2s;
    padding: 4px;

    &-active {
      border-width: 4px;
      padding: 3px;
      border-color: var(--color);
      border-radius: 50%;
    }
  }
}

:deep(.dialog-footer) {
  text-align: right !important;
}

:deep(.dialog-footer > .el-button--default) {
  color: #606266;
  border-color: #dcdfe6;
}

:deep(.dialog-footer > .el-button--default:hover) {
  color: #22af8c;
  border-color: #22af8c;
}
</style>
<style>
.el-popover {
  background-color: #205050;
  color: white;
  border: 0;
  width: 182px;
}

.el-button--mini {
  width: 66px;
  font-size: 14px;
  margin-right: 5px;
}
</style>
