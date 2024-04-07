<template>
  <div>
    <OverviewTitle title="气象监测" />
    <div class="contain-title"></div>
    <div
      class="contain-WeatherTenDayEcharts"
      id="WeatherTenDayEcharts"
      ref="echarWeatherTenDayEchartsts"
    ></div>
  </div>
</template>
<script setup>
import * as echarts from "echarts";
import OverviewTitle from "@/components/OverviewTitle/index.vue";
// import { createWeatherTenDayEcharts } from './help';

const props = defineProps({
  echartsInfo: {
    type: Object,
    default: () => ({}),
  },
});
const echarWeatherTenDayEchartsts = ref();
const echartsRef = ref();
watch(
  () => props.echartsInfo,
  () => {
    props.echartsInfo?.dataTime?.length &&
      props.echartsInfo?.maximumTemperature?.length &&
      props.echartsInfo?.minimumTemperature?.length &&
      setOptions();
  },
  { deep: true, immediate: true }
);
function getWeather() {
  !echartsRef.value &&
    (echartsRef.value = echarts.init(echarWeatherTenDayEchartsts.value));
}
function setOptions() {
  getWeather();
  const { dataTime, maximumTemperature, minimumTemperature } =
    props.echartsInfo;
  echartsRef.value?.setOption({
    textStyle: {
      color: "rgba(255,255,255,0.78)",
    },
    grid: {
      top: "70px",
      right: "30px",
      bottom: "35px",
      left: "40px",
    },
    tooltip: {
      trigger: "axis",
      padding: [0, 15, 0, 15],
      backgroundColor: "#021D1F",
      borderColor: "#006871",
      width: 55,
      position: "top",
      axisPointer: {
        type: "line",
        lineStyle: {
          type: "dotted",
          width: 0.5,
          color: "rgba(255,255,255,0.78)",
          cap: "none",
        },
      },
      textStyle: {
        color: "rgba(255,255,255,0.78)",
        fontSize: 15,
        fontFamily: "PingFang TC-Medium, PingFang TC",
      },
      extraCssText: "box-shadow: 0px 0px 12px 0px rgba(0,228,247,0.36);",
      formatter:
        '<div style="text-align: center;font-size: 8px;">{a}</div><div style="text-align: center;">{c0}℃</div><div style="text-align: center;font-size: 8px;">{a1}</div><div style="text-align: center;">{c1}℃</div>',
    },
    legend: {
      right: 0,
      top: "5",
      textStyle: {
        color: "rgba(255,255,255,0.7)",
      },
    },
    xAxis: {
      type: "category",
      data: dataTime,
      axisPointer: {
        show: true,
      },
      axisTick: {
        show: false,
        alignWithLabel: true,
      },
      axisLine: {
        lineStyle: {
          color: "rgba(255,255,255,0.25)",
        },
      },
      axisLabel: {
        fontFamily: "DIN Alternate-Bold, DIN Alternate",
        // interval: 0,
      },
    },
    yAxis: {
      type: "value",
      axisLabel: {
        formatter: "{value} °C",
        color: "#fff",
        fontFamily: "DIN Alternate-Bold, DIN Alternate",
      },
      splitLine: {
        lineStyle: {
          color: ["rgba(255,255,255,0.25)"],
        },
      },
    },
    series: [
      {
        name: "最高温度",
        type: "line",
        symbol: "circle",
        symbolSize: 6,
        data: maximumTemperature,
        itemStyle: {
          color: "#200202",
          borderColor: "#FCF366",
          borderWidth: 1,
          lineStyle: {
            color: "#FCF366",
          },
        },
        emphasis: {
          itemStyle: {
            color: "#FCF366",
            borderColor: "#FCF366",
            borderWidth: 2,
          },
        },
        areaStyle: {
          origin: "start",
          color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
            {
              offset: 0,
              color: "rgba(252,243,102,0.1)",
            },
            {
              offset: 1,
              color: "rgba(252,243,102,0.3)",
            },
          ]),
        },
      },
      {
        name: "最低温度",
        type: "line",
        data: minimumTemperature,
        symbol: "circle",
        symbolSize: 6,
        itemStyle: {
          color: "#200202",
          borderColor: "#00E4F7",
          borderWidth: 1,
          lineStyle: {
            color: "#00E4F7",
          },
        },
        emphasis: {
          itemStyle: {
            color: "#00E4F7",
            borderColor: "#00E4F7",
            borderWidth: 2,
          },
        },
        areaStyle: {
          origin: "start",
          color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
            {
              offset: 0,
              color: "rgba(0,228,247,0.1)",
            },
            {
              offset: 1,
              color: "rgba(0,228,247,0.5)",
            },
          ]),
        },
      },
    ],
  });
}

onMounted(() => {
  getWeather();
});
</script>
<style lang="scss" scoped>
.contain {
  &-title {
    width: 425px;
    height: 30px;
    background-image: url("https://oss.airoteach.cn/4545e6b3c68a80dcc074b47d813af2b528965b3de12f16e15aafbc8a11d25c70.png");
    background-size: cover;
    position: absolute;
  }

  &-WeatherTenDayEcharts {
    height: 245.72px;
    width: 405px;
  }
}
</style>
