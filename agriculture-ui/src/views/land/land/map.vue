<template>
  <div class="area">
    <MapLight :zIndex="401" />
    <!-- <LandMap class="area-map" :farm="farm" :landList="landList" /> -->
    <!-- 右侧布局 -->
    <MapRightLayout>
      <template #top-0>
        <TopBtn
          :currentEditName="arealistMap[current]?.name"
          :show="topBtnShow"
          @drawFarm="drawFarm"
          @editCancel="editCancel"
          @editSubmit="editLand"
          @draw="draw"
          @disDraw="disDraw"
        />
        <div style="width: 40px; height: 40px;">占位</div>
      </template>
      <template #top-1>
        <LayerSelect :list="showStatuslist" v-model="status" />
      </template>
      <template #top-2>
        <BtmTool :list="TOOLS_CONFIG" @clickTool="handleTools" />
      </template>
      <template #top-3>
        <Legend :list="showRaiseCrops" @handleIcon="handleIcon" />
      </template>
    </MapRightLayout>
    <!-- 地块弹窗 -->
    <div class="popup-container" ref="popup" v-show="popupShow">
      <img class="popup-container-topBorder" src="https://oss.airoteach.cn/9a8e5334fb73ee89c95f8c3944949d75c9d2d98ec36a9d6df8b6d52e84551b3e.png" />
      <MapPopup
        :title="arealistMap?.[current]?.name"
        :info="arealistMap[current]"
        :popupShow="popupShow"
        @deleteArea="deleteArea"
        @editBoundary="editBoundary"
      />
      <img class="popup-container-bottomBorder" src="https://oss.airoteach.cn/4aaf6ace8e56d96655f34b1831d967be42aabcf6e6118d3f559162c647497dfe.png" />
    </div>

    <div ref="mapContainer" class="area-map">
      <div
        class="search"
        @mouseenter="hover = true"
        @mouseleave="hover = false"
      >
        <LandSearchList
          @clickItem="handleSearchChange"
          @loadMore="handleLoadMore"
          @search="handleSearch"
          :list="landPage"
          :total="total"
          :loading="loading"
        />
      </div>

      <!-- 面积提示 -->
      <el-dialog title="提示" center v-model="dialogVisible" width="30%">
        <span>{{ dialogInfo }}</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">
            确 定
          </el-button>
        </span>
      </el-dialog>

      <el-dialog
        :title="title"
        v-model="open"
        :before-close="cancel"
        width="800px"
      >
        <div
          class="dialog-container"
          @mouseenter="hover = true"
          @mouseleave="hover = false"
        >
          <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <el-form-item label="地块名称" prop="landName">
              <el-input v-model="form.landName" placeholder="请输入地块名称" />
            </el-form-item>
            <el-form-item label="种植面积(亩)" prop="area">
              <el-input-number
                :step="0.01"
                v-model="form.area"
                placeholder="请输入种植面积"
              />
            </el-form-item>
            <!-- cropsCreateReqVOList -->
            <el-form-item label="作物及品种" prop="cropsCreateReqVOList">
              <div class="crop-createlist">
                <div
                  v-for="(item, index) in form.cropsCreateReqVOList"
                  :key="index"
                  class="crop-createlist-item"
                >
                  <el-form
                    :model="form.cropsCreateReqVOList[index]"
                    :ref="`form${index}`"
                    :rules="rulesForDetail"
                    label-width="120px"
                  >
                    <div class="crop-createlist-item-info">
                      <!-- 种植作物 -->
                      <el-form-item
                        class="crop-createlist-item-info-formitem"
                        prop="crops"
                      >
                        <div class="crop-select">
                          <el-select
                            :value="form.cropsCreateReqVOList[index].crops"
                            @change="
                              (val) => {
                                form.cropsCreateReqVOList[index].crops = val;
                                form.cropsCreateReqVOList[index].cropsType = '';
                              }
                            "
                            filterable
                            placeholder="请选择"
                          >
                            <el-option
                              v-for="{ val, label } in cropList"
                              :key="val"
                              :label="label"
                              :value="val"
                            >
                            </el-option>
                          </el-select>
                        </div>
                      </el-form-item>
                      <!--  -->
                      <el-form-item
                        v-if="
                          form.cropsCreateReqVOList[index].crops ===
                          OTHER_CROP.val
                        "
                        class="crop-createlist-item-info-formitem"
                        prop="cropsOtherContent"
                      >
                        <el-input
                          v-model="
                            form.cropsCreateReqVOList[index].cropsOtherContent
                          "
                          placeholder="请输入种植作物名称"
                        />
                      </el-form-item>
                      <!--  -->
                      <el-form-item
                        v-if="
                          form.cropsCreateReqVOList[index].crops !==
                          OTHER_CROP.val
                        "
                        class="crop-createlist-item-info-formitem"
                        prop="cropsType"
                      >
                        <el-select
                          v-model="form.cropsCreateReqVOList[index].cropsType"
                          filterable
                          placeholder="请选择"
                        >
                          <el-option
                            v-for="item2 in raiseCropsMap?.[
                              form.cropsCreateReqVOList[index].crops
                            ]?.cropsVarietiesList?.map(
                              ({ code: value, cropsVarietiesName: label }) => ({
                                value,
                                label,
                              })
                            )"
                            :key="item2.value"
                            :label="item2.label"
                            :value="item2.value"
                          >
                          </el-option>
                        </el-select>
                      </el-form-item>
                      <!--  -->
                      <el-form-item
                        v-if="
                          form.cropsCreateReqVOList[index].cropsType ===
                            OTHER_CROP_TYPE.value ||
                          form.cropsCreateReqVOList[index].crops ===
                            OTHER_CROP.val
                        "
                        class="crop-createlist-item-info-formitem"
                        prop="cropsTypeOtherContent"
                      >
                        <el-input
                          v-model="
                            form.cropsCreateReqVOList[index]
                              .cropsTypeOtherContent
                          "
                          placeholder="请输入作物品种名称"
                        />
                      </el-form-item>
                    </div>
                  </el-form>
                  <div class="crop-createlist-item-btn">
                    <el-button
                      icon="Remove"
                      circle
                      @click="removeOne(index)"
                    />
                    <el-button
                      icon="CirclePlus"
                      circle
                      @click="addOne"
                    />
                  </div>
                </div>
              </div>
            </el-form-item>
            <el-form-item label="联系人姓名" prop="contacts">
              <el-input
                v-model="form.contacts"
                placeholder="请输入联系人姓名"
              />
            </el-form-item>
            <el-form-item label="联系电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入联系电话" />
            </el-form-item>
            <el-form-item label="颜色" prop="color">
              <div class="color-select">
                <div
                  @click="form.color = val"
                  v-for="val in COLOR_LIST"
                  :key="val"
                  :class="{
                    'color-select-item': true,
                    'color-select-item-active': form.color === val,
                  }"
                  :style="`background-color:${val};--color:${val}`"
                ></div>
              </div>
            </el-form-item>
            <el-form-item label="绑定计划" prop="planName">
              <el-select
                v-model="form.planName"
                placeholder="请选择计划名称"
                :disabled="disabled"
              >
                <el-option
                  v-for="item in planNameArray"
                  :label="item.planName"
                  :value="item.plantingPlanId"
                  :key="item.plantingPlanId"
                />
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </div>
        </div>
      </el-dialog>
    </div>
    <!-- 加载状态 -->
    <div class="loading" v-show="wholeLoading">
      <BLoading />
    </div>
  </div>
</template>

<script setup>
import MapRightLayout from './components/RightLayout.vue'
import LayerSelect from './components/LayerSelect.vue'
import BtmTool from './components/BtmTool.vue'
import Legend from './components/Legend.vue'
import MapPopup from './components/MapPopup.vue'
import TopBtn from './components/TopBtn.vue'
import BLoading from "@/components/BLoading/index.vue";
import LandSearchList from "@/components/LandSearchList/index.vue";
import MapLight from "@/components/MapLight/index.vue";

import { getQueryLandPlan } from "@/api/agro/taskInfo.js";
import { getFarmByTenant, update as updateFarm } from "@/api/farm/farm.js";
import { deleteLand, updateLand } from "@/api/land/land.js";
import { createLand } from "@/api/land/map";
import { STORE_KEY, getStore } from "@/utils";
import { getTenantId } from "@/utils/auth";
import { UNDEFINED } from "@/constants"

import {
  diffLandCoordinate,
  formatLandCoordinate,
  formatLandCoordinate2Arr,
  formatLandCoordinateCenter,
} from "@/utils/latlng.js";
import { getPath } from "@/utils/ruoyi";
import "@geoman-io/leaflet-geoman-free";
import "@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css";
import { Remove, CirclePlus } from "@element-plus/icons-vue";
import { ElNotification } from 'element-plus'
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import {
  COLOR_LIST,
  CONFIG,
  FORM_TYPE_ENUM,
  OTHER_CROP,
  OTHER_CROP_TYPE,
  TOOLS_CONFIG,
  TOP_BTN_SHOW_ENUM,
  calcArea,
  createMap,
  createMarkerIcons,
  createPolygon,
  createTextIcon,
  filterIcons,
  findLayerInGroupByParam,
  fomartShowRaiseCrops,
  fomatCreateLand,
  formatCropsList,
  formatSearchOptions,
  formatShowStatuslist,
  getStatusFromCache,
  getStatuslist,
  iplocateAndFit,
  isOverBoundary,
  setStatusStore,
  validateArea,
  validateContacts,
  validateCropsCreateReqVOList,
  validateCropsOtherContent,
  validateCropsTypeOtherContent,
  validateLandName,
  validateTel,
  zoomAndCenter,
  zoomAndCenterExactArr
} from "./help";

import useUserStore from '@/store/modules/user'
import useLandMapStore from '@/store/modules/landMap'
import { ref, toRefs, computed, onMounted, watch } from 'vue';

const userStore = useUserStore()
const landMapStore = useLandMapStore()
const { total, raiseCrops, raiseCropsMap, landPage, arealistMap, arealist } = toRefs(landMapStore)

const { proxy } = getCurrentInstance()

const data = reactive({
  map: null,
  showIcons: [],
  wholeLoading: true,
  loading: false,
  topBtnShow: TOP_BTN_SHOW_ENUM.NO_DRAWING,
  form: {},
  editPrev: [],
  editAfter: [],
  statusMapLayerGroup: {},
  // 画地块临时图层
  drawLayer: null,
  // 画地块弹框标题
  title: "添加地块",
  // 计划名称数组
  planNameArray: [],
  disabled: false,
  // 表单校验
  rules: {
    landName: [
      {
        required: true,
        validator: validateLandName,
        trigger: "change",
      },
    ],
    area: [
      {
        required: true,
        validator: validateArea,
        trigger: "change",
      },
    ],
    crops: [
      {
        required: true,
        message: "请选择作物",
        trigger: "change",
      },
    ],
    cropsCreateReqVOList: [
      {
        required: true,
        validator: validateCropsCreateReqVOList,
        trigger: "change",
      },
    ],
    cropsOtherContent: [
      {
        required: true,
        validator: validateCropsOtherContent,
        trigger: "change",
      },
    ],
    cropsType: [
      {
        required: true,
        message: "请输入作物品种",
        trigger: "change",
      },
    ],
    cropsTypeOtherContent: [
      {
        required: true,
        validator: validateCropsTypeOtherContent,
        trigger: "change",
      },
    ],
    contacts: [
      {
        required: true,
        validator: validateContacts,
        trigger: "change",
      },
    ],
    tel: [
      {
        required: true,
        validator: validateTel,
        trigger: "change",
      },
    ],
    color: [
      {
        required: true,
        message: "请选择颜色",
        trigger: "change",
      },
    ],
    planName: [
      {
        required: true,
        message: "请选择种植计划，没有计划，可选择“无”",
        trigger: "change",
      },
    ],
  },
  // 表单
  open: false,
  // 初始化不展示弹窗
  popupShow: false,
  // x亩限制文案
  dialogInfo: "",
  // x亩限制弹窗展示
  dialogVisible: false,
  // 当前被点击的地块id
  current: -1,
  // 搜索文案
  serachValue: "",
  // 展示哪些图层备选
  statuslist: [],
  // 展示哪些图层
  status: [],
  // 正在画
  drawing: false,
  init: false,
  hover: false,
  farmPosVoList: [],
  farmId: UNDEFINED,
  rulesForDetail: {
    crops: [
      {
        required: true,
        message: "请选择作物",
        trigger: "change",
      },
    ],
    cropsOtherContent: [
      {
        required: true,
        validator: validateCropsOtherContent,
        trigger: "change",
      },
    ],
    cropsType: [
      {
        required: true,
        message: "请输入作物品种",
        trigger: "change",
      },
    ],
    cropsTypeOtherContent: [
      {
        required: true,
        validator: validateCropsTypeOtherContent,
        trigger: "change",
      },
    ],
  },
})
const { 
  map,
  showIcons, 
  wholeLoading, 
  loading,
  topBtnShow,
  form,
  editPrev,
  editAfter,
  statusMapLayerGroup,
  drawLayer,
  title,
  planNameArray,
  disabled,
  rules,
  open,
  popupShow,
  dialogInfo,
  dialogVisible,
  current,
  serachValue,
  statuslist,
  status,
  drawing,
  init,
  hover,
  farmPosVoList,
  farmId,
  rulesForDetail
} = toRefs(data)

const showRaiseCrops = computed(() => {
  return fomartShowRaiseCrops(arealist.value, raiseCrops.value)
})
const showStatuslist = computed(() => {
  return formatShowStatuslist(statuslist.value)
})
const cropList = computed(() => {
  return formatCropsList(raiseCrops.value)
})
const searchOptions = computed(() => {
  return formatSearchOptions(raiseCropsMap.value, form.value.crops)
})

async function initFunc() {
  getFarmInfo()
  await landMapStore.loadRaiseCrops()
  loadAreaList()
  reset()
  handleSearch()
  nextTick(() => {
    map.value = createMap(proxy.$refs.mapContainer);
    initLayers();
    initDraw();
  })
  init.value = true
}

// 获取地块
function loadAreaList() {
  wholeLoading.value = true
  landMapStore.loadAreaList().finally(() => {
    wholeLoading.value = false
  })
}
function handleIcon(){
  if(showIcons.value.includes(icon)) {
    const index = showIcons.value.indexOf(icon)
    showIcons.value.splice(index, 1)
  } else {
    showIcons.value.push(icon)
  }
  freshLayer()
}

function getPlanName() {
  getQueryLandPlan().then((response) => {
    planNameArray.value = response.data;
    planNameArray.value.unshift({ planName: "无", plantingPlanId: -1 });
  });
}
function calcSearchOptions(crops) {
  return formatSearchOptions(raiseCropsMap.value, crops);
}
// dialog-删除种植的作物
function removeOne(index) {
  if (form.value.cropsCreateReqVOList.length === 1) {
    proxy.$modal.msgError("至少保留一个作物");
    return;
  }
  form.value.cropsCreateReqVOList.splice(index, 1);
}
// dialog-添加种植的作物
function addOne() {
  form.value.cropsCreateReqVOList.push({
    crops: raiseCrops.value[0]?.code,
    cropsIsOther: 0,
    cropsTypeIsOther: 0,
    cropsType: raiseCrops.value[0]?.cropsVarietiesList?.[0]?.code,
    cropsOtherContent: "",
    cropsTypeOtherContent: "",
  });
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
      const { posVoList, id } = data || {};
      farmPosVoList.value = posVoList || [];
      topBtnShow.value = farmPosVoList.value.length
        ? TOP_BTN_SHOW_ENUM.NO_DRAWING
        : TOP_BTN_SHOW_ENUM.FARM;
      window.clearFarm = () =>
        updateFarm({ id: farmId.value, posVoList: [] });
      farmId.value = id;
    })
    .catch((res) => {
      proxy.$alert("当前功能不可用，请联系管理员", "错误", {
        confirmButtonText: "确定",
        callback: (action) => {
          proxy.$router.push("/");
        },
      });
    });
}
function handleDialogClose() {
  cancel();
}
function handleTools(type) {
  const functionMap = {
    fresh: handleToolfresh,
    locate: handleToollocate,
    plus: handleToolplus,
    minus: handleToolminus,
  };
  functionMap[type]?.();
}

function handleToolfresh() {
  loadAreaList();
}
function handleToollocate() {
  iplocateAndFit(map.value).catch(() => {
    proxy.$modal.msgError("定位失败");
  });
}
function handleToolplus() {
  map.value.zoomIn();
}
function handleToolminus() {
  map.value.zoomOut();
}
function handleLoadMore(landName) {
  loading.value = true;
  landMapStore.loadPageLand({ landName })
    .then(() => {
      loading.value = false;
    })
    .catch(() => {
      loading.value = false;
    });
}
function handleSearch(landName) {
  loading.value = true;
  landMapStore.loadPageLandSearch({ landName })
    .then(() => {
      loading.value = false;
    })
    .catch(() => {
      loading.value = false;
    });
}
function closeDraw() {
  topBtnShow.value = farmPosVoList.value.length
    ? TOP_BTN_SHOW_ENUM.NO_DRAWING
    : TOP_BTN_SHOW_ENUM.FARM;
}
function editCancel(isDefualt = true) {
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.AreaLayer.value];
  const layer = findLayerInGroupByParam(layerGroup, current.value, "_id");
  if (layer) {
    // 关闭编辑，恢复之前的状态
    layer.pm.disable();
    isDefualt && layer.setLatLngs(editPrev.value);
    closeDraw();
  }
}
function editLand() {
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.AreaLayer.value];
  const layer = findLayerInGroupByParam(layerGroup, current.value, "_id");
  const [landCoordinate] = editAfter.value || [];
  const {
    name: landName,
    contacts,
    tel,
    color,
    cropsCreateReqVOList,
    area,
    pos,
    id,
    planName,
    planId
  } = arealistMap.value[current.value] || {};
  if (!id || !layer) {
    return;
  }
  layer.pm.disable();
  closeDraw();
  open.value = true;
  title.value = "编辑地块";
  const isEdited = diffLandCoordinate(pos, landCoordinate);
  form.value = {
    color,
    id,
    landName,
    cropsCreateReqVOList,
    area: isEdited ? calcArea(layer) : area,
    posVoList: formatLandCoordinate(landCoordinate),
    landCoordinateCenter: formatLandCoordinateCenter(landCoordinate),
    contacts,
    tel,
    planName,
    planId
  }
  if(form.value.planId == null) {
    form.value.planName = -1
  } else {
    form.value.planName = form.value.planId
    planNameArray.value.push({planName, plantingPlanId: planId});
  }
}
// 编辑轮廓
function editBoundary() {
  if (!status.value.includes(CONFIG.LAYERS.AreaLayer.value)) {
    ElNotification.warning("请先打开地块轮廓开关");
    return;
  }
  if (!status.value.includes(CONFIG.LAYERS.FarmLayer.value)) {
    ElNotification.warning("请先打开农场轮廓开关");
    return;
  }
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.AreaLayer.value];
  const layer = findLayerInGroupByParam(layerGroup, current.value, "_id");
  if (layer) {
    map.value.closePopup();
    layer.pm.enable();
    editPrev.value = layer._latlngs;
    topBtnShow.value = TOP_BTN_SHOW_ENUM.EDITING;
    editAfter.value = layer._latlngs.concat();
    layer.on("pm:edit", () => {
      editAfter.value = layer._latlngs.concat();
    });
  }
}
// 待优化
function initCenter() {
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.FarmLayer.value];
  const res = [];
  layerGroup && layerGroup.eachLayer(({ _latlngs: latlngs } = {}) => {
    res.push(latlngs);
  });
  if (res.length === 0) {
    init.value = true;
    return;
  }
  if (!init.value) {
    return;
  }
  zoomAndCenter(map.value, res, layerGroup, 100);
  init.value = false;
}
// 初始化画图
function initDraw() {
  map.value.pm.setLang("zh");
}
function calcAreaFunc(layer) {
  form.value.area = calcArea(layer);
  if (form.value.area > CONFIG.MAX_AREA) {
    dialogVisible.value = true;
    dialogInfo.value = `当前版本地块最大面积为${CONFIG.MAX_AREA}亩`;
    return false;
  }
  return true;
}
function handleSearchChange(id) {
  if (!status.value.includes(CONFIG.LAYERS.AreaLayer.value)) {
    status.value.push(CONFIG.LAYERS.AreaLayer.value);
    freshLayer();
  }
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.AreaLayer.value];
  const exactArr = [];
  let activeLayer = null;
  layerGroup?.eachLayer((layer) => {
    if (layer._id === id) {
      activeLayer = layer;
    } else {
      exactArr.push(layer);
    }
  });
  zoomAndCenterExactArr(map.value, activeLayer?._latlngs, exactArr);
  activeLayer?.setStyle({ fillOpacity: CONFIG.FILL_OPACITY.HIGHLIGHT })
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
function freshLayer() {
  clearLayers();
  status.value.forEach((item) => {
    const layerGroup = statusMapLayerGroup.value[item];
    ({
      [CONFIG.LAYERS.AreaLayer.value]: createAreaLayer,
      [CONFIG.LAYERS.IconLayer.value]: createIconLayer,
      [CONFIG.LAYERS.TextLayer.value]: createTextLayer,
      [CONFIG.LAYERS.FarmLayer.value]: createFarmLayer,
    })?.[item]?.(layerGroup);
  });
  // 首次创建成功后，自动聚焦
  initCenter();
}
// 清空所有图层
function clearLayers() {
  Object.values(statusMapLayerGroup.value).forEach((layerGroup) => {
    layerGroup.clearLayers();
  });
}
function bindPopup({ latlng: { lat, lng } = {} }, id) {
  if (topBtnShow.value === TOP_BTN_SHOW_ENUM.EDITING) {
    ElNotification.warning("请先保存地块");
    return;
  }
  current.value = id;
  popupShow.value = true;
  const popup = L.popup();
  popup
    .setLatLng({ lat, lng })
    .setContent(proxy.$refs.popup)
    .openOn(map.value);
}
// 创建轮廓图层
function createAreaLayer(layerGroup) {
  arealist.value.forEach(({ pos, color, id, cropsCreateReqVOList }) => {
    const _icons = cropsCreateReqVOList.map(item=>item.crops).join('-')
    const p = createPolygon(pos, { color, _icons }, id, (e) =>
      bindPopup(e, id)
    );
    layerGroup.addLayer(p);
  });
}
// 创建icon图层
function createIconLayer(layerGroup) {
  arealist.value
    .filter(item => filterIcons(item, showIcons.value))
    .filter(({ center }) => center)
    .forEach(({ center: pos, cropsCreateReqVOList, id }) => {
      const allCrops = [
        ...new Set(cropsCreateReqVOList.map(({ crops }) => crops)),
      ];
      allCrops.forEach((crops, index) => {
        const iconUrl =
          raiseCropsMap.value[crops]?.imageUrl ||
          "/images/crops/crop.png";
        const p = L.marker(pos, {
          icon: createMarkerIcons({ iconUrl }, index, allCrops.length),
        });
        p.on("click", (e) => bindPopup(e, id));
        layerGroup.addLayer(p);
      });
    });
}
// 创建农场边界图层
function createFarmLayer(layerGroup) {
  if (!farmPosVoList.value || !farmPosVoList.value?.length) {
    return;
  }
  const pane = map.value.getPane(`pane-${CONFIG.LAYERS.FarmLayer.value}`);
  layerGroup.addLayer(
    createPolygon(
      formatLandCoordinate2Arr(farmPosVoList.value),
      {
        color: "#ffffffaa",
        pane,
      },
      "farmBoundary",
      false,
      false
    )
  );
  // console.log(calcArea(layer, true) / 100_0000);
}
// 创建文字图层
function createTextLayer(layerGroup) {
  arealist.value
    .filter(({ center }) => center)
    .forEach(({ name: text, center: pos, id }) => {
      const p = L.marker(pos, {
        icon: createTextIcon(text, { iconAnchor: [30, 60] }),
      });
      p.on("click", (e) => bindPopup(e, id));
      layerGroup.addLayer(p);
    });
}
function drawFarm() {
  topBtnShow.value = TOP_BTN_SHOW_ENUM.DRAWING;
  map.value.pm.enableDraw("Polygon", { snappable: false });
  map.value.on("pm:create", (e) => {
    drawLayer.value = e.layer;
    const [coordinate] = drawLayer.value._latlngs || [];
      Promise.resolve()
      .then(() =>
        updateFarm({
          id: farmId.value,
          posVoList: formatLandCoordinate(coordinate),
          coordinateCenter: formatLandCoordinateCenter(coordinate),
        })
      )
      .then(() => {
        if (!status.value.includes(CONFIG.LAYERS.FarmLayer.value)) {
          status.value.push(CONFIG.LAYERS.FarmLayer.value);
        }
        getFarmInfo();
        disDraw();
      })
      .catch((error) => {
        disDraw();
      });
  });
}
function draw() {
  if (!status.value.includes(CONFIG.LAYERS.FarmLayer.value)) {
    ElNotification.warning("请先打开农场轮廓开关");
    return;
  }
  const layerGroup =
    statusMapLayerGroup.value[CONFIG.LAYERS.FarmLayer.value];
  const farmBoundaryLayer = findLayerInGroupByParam(
    layerGroup,
    "farmBoundary",
    "_id"
  );
  topBtnShow.value = TOP_BTN_SHOW_ENUM.DRAWING;
  map.value.pm.enableDraw("Polygon", { snappable: false });
  map.value.on("pm:create", (e) => {
    drawLayer.value = e.layer;
    closeDraw();
    if (!calcAreaFunc(drawLayer.value)) {
      disDraw();
      return;
    }
    if (isOverBoundary(farmBoundaryLayer, drawLayer.value)) {
      dialogVisible.value = true;
      dialogInfo.value = "不允许超出农场边界";
      disDraw();
      return;
    }
    title.value = "添加地块";
    open.value = true;
    const [landCoordinate] = e?.layer?._latlngs || [];
    form.value.posVoList = formatLandCoordinate(landCoordinate);
    form.value.landCoordinateCenter =
      formatLandCoordinateCenter(landCoordinate);
  });
}
function disDraw() {
  drawLayer.value && map.value.removeLayer(drawLayer.value);
  closeDraw();
  map.value.pm.disableDraw();
}
/** 取消按钮 */
function cancel() {
  editCancel(title.value !== "添加地块");
  open.value = false;
  disDraw();
  reset();
  getPlanName();
}
function reset() {
  const { mobile } = getStore(STORE_KEY.USER_INFO);
  form.value = {
    color: COLOR_LIST[0],
    id: UNDEFINED,
    landName: UNDEFINED,
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
    area: UNDEFINED,
    contacts: userStore.name,
    tel: mobile,
    landCoordinate: UNDEFINED,
    landCoordinateCenter: UNDEFINED,
  };
}
function deleteArea() {
  const { id } = arealistMap.value[current.value] || {};
  deleteLand(id).then(() => {
    current.value = -1;
    popupShow.value = false;
    map.value.closePopup();
    loadAreaList();
    handleSearch();
    proxy.$modal.msgSuccess("删除成功");
  });
}
function submitForm() {
  proxy.$refs.form.validate((valid) => {
    if (!valid) {
      return;
    }
    form.value.planId = form.value.planName;
    Promise.all(
      form.value.cropsCreateReqVOList.map(
        (_, index) =>
          new Promise((resolve, reject) => {
            const formIndex = `form${index}`;
            proxy.$refs[formIndex]?.[0]?.validate((valid) => {
              if (!valid) {
                reject();
              } else {
                resolve();
              }
            });
          })
      )
    )
      .then((res) => {
        if (form.value.id != null) {
          updateLand(fomatCreateLand(form.value))
            .then(() => {
              proxy.$modal.msgSuccess("修改成功");
              open.value = false;
              reset();
              loadAreaList();
              handleSearch();
              getPlanName();
            })
            .catch(() => {
              editCancel();
            });
          return;
        }
        createLand(fomatCreateLand(form.value)).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          reset();
          disDraw();
          loadAreaList();
          handleSearch();
          getPlanName();
        });
      })
      .catch((error) => {
        console.log(error);
      });
  });
}
onMounted(() => {
  initFunc()
  getPlanName()
})
watch(arealist, () => {
  freshLayer()
})
watch(status, () => {
  setStatusStore(status.value)
  freshLayer()
})
watch(farmPosVoList,() => {
  freshLayer()
})
watch(hover, () => {
  if (hover.value) {
    map.value.dragging.disable();
    map.value.scrollWheelZoom.disable();
  } else {
    map.value.dragging.enable();
    map.value.scrollWheelZoom.enable();
  }
})
</script>

<style scoped lang="scss">
@import "./styles/popup.scss";
.area {
  position: relative;
  &-map {
    height: calc(100vh - 96px);
    position: relative;
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

.loading {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
  z-index: 999;
}
</style>
