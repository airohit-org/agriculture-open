<template>
  <AdaptLayout :loading="loading" :modalVis="modalVis" @toggleModal="toggleModal">
    <template v-slot:bg>
      <img class="crops-center-bg" src="https://oss.airoteach.cn/2520734962349f85e41562459f8c83f011d0c67acaf10d849d7f7d478d5b5806.jpg" alt="">
    </template>
    <template v-slot:loading>
      <BLoading />
    </template>
    <template v-slot:title>
      <FarmTitle :title="farmName" />
    </template>
    <!-- 作物信息 -->
    <template v-slot:left-1>
      <CropInformation />
    </template>
    <!-- 出苗率 -->
    <template v-slot:left-2>
      <EmergenceRate />
    </template>
    <!-- 农事任务 -->
    <template v-slot:left-3>
      <AgriculturalTasks />
    </template>

    <!-- 中间部分 -->
    <template v-slot:right-top-1>
      <InnerCenter />
    </template>
    <!-- 长势分析 -->
    <template v-slot:right-top-2-1>
      <!-- <GrowthAnalysis /> -->
    </template>
    <!-- 病虫害 -->
    <template v-slot:right-top-2-2>
      <Hazard />
    </template>

    <!-- 种植流程 -->
    <template v-slot:right-bottom>
      <CropPlan :list="planList" @handleClick="handleClick" />
    </template>

    <template v-slot:modal-content>
      <CropsOne />
    </template>

  </AdaptLayout>
</template>

<script setup>
import CropsOne from '@/views/crop/crop/index.vue'
import AdaptLayout from './components/AdaptLayout/AdaptLayout.vue'
import AgriculturalTasks from './components/AgriculturalTasks.vue';
import CropInformation from './components/CropInformation.vue';
import EmergenceRate from './components/EmergenceRate.vue';
// import GrowthAnalysis from './components/GrowthAnalysis.vue';
import Hazard from './components/Hazard.vue';
import InnerCenter from './components/InnerCenter.vue';
import CropPlan from './components/CropPlan.vue';
import FarmTitle from '@/views/overview/components/FarmTitle.vue';
import BLoading from "@/components/BLoading/index.vue";

import useBaseMapStore from '@/store/modules/baseMap'
import { MOCK } from './help'
import { onMounted, ref, toRefs } from 'vue'

const modalVis = ref(false)
const loading = ref(false)
const planList = ref(MOCK.planList)

const baseMapStore = useBaseMapStore()

const { farmName } = toRefs(baseMapStore)

function fresh() {
    loading.value = true;
    baseMapStore.getMapInfo().then(() => {
      loading.value = false;
    }).finally(() => {
      loading.value = false;
    })
}
function toggleModal() {
  modalVis.value = !modalVis.value;
}
function handleClick(item) {
  item.toUrl && toggleModal();
}

onMounted(() => {
  fresh()
})

</script>

<style scoped lang="scss">
.crops-center-bg {
  width: 100%;
  height: 100%;
}
</style>
