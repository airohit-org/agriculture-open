<template>
  <Layout :loading=loading :showAccTempZone="accumulatedTemperatureZone != null || !!warnOneInfoList.length">
		<template #bg>
			<BaseMap :farm="farm" :farmName="farmName" :landList="landList" />
		</template>
		<template #title>
			<FarmTitle :title="farmName" />
		</template>
    <template #info>
      <WarnOneInfoList v-show="warnOneInfoList.length" :list="warnOneInfoList" />
    </template>
    <template #total>
      <WarnTotal v-if="warnTotal.total" :subTitle="warnTotal.subTitle" :total="warnTotal.total" />
    </template>
    <template #left-1>
			<FarmInfo :area="plantArea" :list="farmInfoList" />
		</template>
    <template #left-2>
			<FarmLandList :list=landList />
		</template>
    <template #left-3>
			<FarmAgriculturalTask :list="taskList" />
		</template>
    <template #right-0></template>
    <template #right-1>
      <WeatherForecast 
        :place="farmName" 
        :weather="todayWeather.weather"
        :todayMaxTemperature="todayWeather.todayMaxTemperature" 
        :todayMinTemperature="todayWeather.todayMinTemperature"
        :echartsInfo="weather10" 
        :weatherInfo="todayWeather.weatherInfo" 
        :iconWeather="todayWeather.iconWeather" 
      />
    </template>
    <template #right-2>
      <DeviceStatistics :total="deviceInfo.total" :list="deviceInfo.list" />
    </template>
    <template #loading>
      <BLoading />
    </template>
    <template #accTempZone>
      <AccTempZone v-if="accumulatedTemperatureZone !== null" :active="accumulatedTemperatureZone" />
    </template>
	</Layout>
</template>

<script setup>
import Layout from './components/AdaptLayout/Layout.vue';
import BLoading from "@/components/BLoading/index.vue";
import BaseMap from './components/BaseMap.vue';
import FarmInfo from './components/FarmInfo.vue';
import FarmTitle from './components/FarmTitle.vue';
import FarmLandList from './components/FarmLandList.vue';
import FarmAgriculturalTask from './components/FarmAgriculturalTask/FarmAgriculturalTask.vue';
import WarnOneInfoList from './components/Warn/WarnOneInfoList.vue';
import WarnTotal from './components/Warn/WarnTotal.vue';
import WeatherForecast from './components/WeatherForecast/WeatherForecast.vue';
import DeviceStatistics from './components/DeviceStatistics.vue';
import AccTempZone from './components/AccTempZone.vue';

import useOverviewStore from '@/store/modules/overview';
import useBaseMapStore from '@/store/modules/baseMap';
import { getTenantId, getFarmTenantId } from "@/utils/auth";
import { connectMqtt } from "@/utils/mqttHelp.js";
import { getUserDeviceList } from "@/api/obs/deviceInfo";
import { formatWarnOneInfoList } from './help'
import { DEVICE } from './config'
import { onMounted, reactive, ref, toRefs } from 'vue'

const loading = ref(false)
const warnOneInfoList = ref([])
const warnTotal = reactive({
  total: 0,
  subTitle: ''
})
const deviceInfo = reactive(
  {
    total: DEVICE.total,
    list: DEVICE.list,
  }
) 
const overviewStore = useOverviewStore()
const baseMapStore = useBaseMapStore()
const { plantArea, farmInfoList, taskList, todayWeather, weather10 } = toRefs(overviewStore)
const { farm, farmName, landList, accumulatedTemperatureZone } = toRefs(baseMapStore)

const getUserDevices = async () => {
  let res = await getUserDeviceList({ farmId: getFarmTenantId() })
  let arr = []
  arr = res.data
  const device = {
    total: 0,
    list: [
      {
        id: 1,
        title: '在线',
        value: 0,
        color: "#1FFACE",
      }, {
        id: 2,
        title: '离线',
        value: 0,
        color: "#FCF366",
      }
    ]
  }
  arr.forEach(ele => {
    if(ele.status == 'online'){
      device.list[0].value++
    }else{
      device.list[1].value++
    }
    device.total++
  })
  deviceInfo.total = device.total
  deviceInfo.list = device.list
}

const subscribeMqtt = () => {
  const tenantId = getTenantId();
  tenantId &&
    connectMqtt(tenantId, (data) => {
      warnOneInfoList.value = formatWarnOneInfoList(data);
    });
}

const fresh = () => {
  loading.value = true
  Promise.all([overviewStore.getOverviewInfo(), baseMapStore.getMapInfo()])
    .then(() => {
      loading.value = false;
    })
    .catch(() => {
      loading.value = false;
    });
}

onMounted(() => {
  fresh()
  getUserDevices()
  // subscribeMqtt()
})
</script>
<style lang="scss" scoped>

</style>