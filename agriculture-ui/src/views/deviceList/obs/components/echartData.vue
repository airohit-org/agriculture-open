<template>
  <div class="contrains">
    <div class="contain-echarts" id="deviceDataEcharts" ref="echartsRef"></div>
  </div>
</template>

<script setup>
import * as echarts from "echarts";
import { createOptionEcharts } from "./help";
const typeArr = [
  {
    土壤温度: "℃",
  },
  {
    空气温度: "℃",
  },
  {
    二氧化碳: "ppm",
  },
  {
    土壤水分: "%",
  },
  {
    "pm2.5": "ppm",
  },
  {
    风向: "°",
  },
  {
    pm10: "ppm",
  },
  {
    相对湿度: "%",
  },
  {
    风速: "m/s",
  },
  {
    气压: "hpa",
  },
  {
    降雨量: "mm",
  },
  {
    光照: "klux",
  },
];
const props = defineProps({
  echartsInfo: Object,
  echartDeVType: String,
});
const deviceDataEcharts = ref(null);
const echartsRef = ref();
const { proxy } = getCurrentInstance();
watch(
  () => props.echartsInfo,
  () => {
    setOptions();
  },
  { deep: true, immediate: true }
);

onMounted(() => {
  getDevice();
});
function getDevice() {
  !deviceDataEcharts.value &&
    $nextTick(() => {
      deviceDataEcharts.value = echarts.init(echartsRef.value);
    });
}
function setOptions() {
  getDevice();
  const {
    dataTime,
    maximumTemperature,
    minimumTemperature,
    Temperature1,
    Temperature2,
    Temperature3,
  } = props.echartsInfo;
  const index = typeArr.findIndex((ele, index) => ele[props.echartDeVType]);
  const option = createOptionEcharts(
    dataTime,
    maximumTemperature || [],
    minimumTemperature,
    Temperature1 || [],
    Temperature2 || [],
    Temperature3 || [],
    props.echartDeVType,
    typeArr[index][props.echartDeVType]
  );

  proxy.$nextTick(() => {
    deviceDataEcharts.value?.setOption(option);
  });
}
</script>

<style scoped lang="scss">
.contrains {
  width: 100%;
  height: 100%;
  background-image: url("https://oss.airoteach.cn/f4fe266435f3261bcca836a1944983eaa80eb6a9b73019bd9f5967277e782dea.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;

  .contain-echarts {
    margin: 0 auto;
    height: 340px;
    width: 95%;
  }
}
</style>
