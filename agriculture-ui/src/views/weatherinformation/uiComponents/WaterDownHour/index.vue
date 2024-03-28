<template>
  <div>
    <OverviewTitle title="分钟级降水预报" />
    <div class="contain-rain">
      <div class="contain-rain-place">
        <img
          class="contain-rain-place-fixed"
          src="https://oss.airoteach.cn/7d4f30cb142ab93b8ff4016a71d0eacd75d75fb4ecb55d367c83464f44f90a05.png"
        />
        <div class="contain-rain-place-position">{{ place }}</div>
      </div>
      <div class="contain-rain-forecast">
        <img
          class="contain-rain-forecast-weatherIcon"
          :src="today.iconWeather"
        />
        <div class="contain-rain-forecast-weather">{{ today.weather }}</div>
        <div class="contain-rain-forecast-temperature">
          {{ today.todayMinTemperature }}℃~{{ today.todayMaxTemperature }}℃
        </div>
      </div>
    </div>
    <div class="contain-rain-information">{{ information }}</div>
    <div
      class="contain-WaterEcharts"
      id="WaterEcharts"
      ref="WaterEcharts"
    ></div>
  </div>
</template>
<script setup>
import * as echarts from "echarts";
import weatherIcon from "@/assets/images/Overview/weather.png";
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import { debounceOneline as debounce } from "@/utils";

const props = defineProps({
  echartsInfo: Object,
  place: String,
  today: Object,
  msgList: {
    type: Array,
    default: () => [],
  },
});
const WaterEcharts = ref();
const hoverIndex = ref(-1);
const echartsContainer = ref();
watch(
  () => props.echartsInfo,
  () => {
    props.echartsInfo?.rainfallInfo?.length &&
      props.echartsInfo?.timeDataInfo?.length &&
      getWater();
  },
  { immediate: true, deep: true }
);

const information = computed(() => {
  return props.msgList[hoverIndex.value];
});
function initEchart() {
  if (!echartsContainer.value) {
    echartsContainer.value = echarts.init(WaterEcharts.value);
    echartsContainer.value
      .getZr()
      .on("mousemove", debounce(onEchartHover, 200));
    echartsContainer.value
      .getZr()
      .on("mouseout", () => (hoverIndex.value = -1));
  }
}
function onEchartHover(params) {
  const pointInPixel = [params.offsetX, params.offsetY];
  const isInner = echartsContainer.value.containPixel("grid", pointInPixel);
  if (isInner) {
    let [x] = echartsContainer.value.convertFromPixel(
      { seriesIndex: 0 },
      pointInPixel
    );
    hoverIndex.value = x;
  }
}
function getWater() {
  initEchart();
  echartsContainer.value?.setOption({
    grid: {
      top: "20px",
      right: "30px",
      bottom: "20px",
      left: "52px",
    },
    xAxis: {
      type: "category",
      data: props.echartsInfo.timeDataInfo,
      boundaryGap: false,
      axisTick: {
        show: false,
        interval: "2",
      },
      axisLabel: {
        color: "#fff",
        fontFamily: "DIN",
      },
      axisLine: {
        lineStyle: {
          color: ["rgba(255,255,255,0.25)"],
        },
      },
    },
    yAxis: {
      type: "value",
      axisTick: {
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: ["rgba(255,255,255,0.25)"],
        },
      },
      axisLabel: {
        margin: "14",
        hideOverlap: false,
        color: "#fff",
        fontFamily: "DIN",
        formatter: "{value}mm",
      },
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        label: {
          backgroundColor: "#6a7985",
        },
      },
      formatter: (params) => {
        return `<div style="font-size: 11px;text-align: center;color: #fff;">
                            ${
                              params[0].axisValue
                            }</div><div style="font-size: 9px;text-align: center;color: #fff;">降水量${
          params[0].data
        }</div><div style="font-size: 9px;text-align: center;color: #fff;">
                                        ${(function () {
                                          if (
                                            params[0].data >= 0 &&
                                            params[0].data < 10
                                          ) {
                                            return "小雨";
                                          } else if (
                                            params[0].data >= 10 &&
                                            params[0].data < 25
                                          ) {
                                            return "中雨";
                                          } else if (
                                            params[0].data >= 25 &&
                                            params[0].data < 50
                                          ) {
                                            return "大雨";
                                          } else if (
                                            params[0].data >= 50 &&
                                            params[0].data < 100
                                          ) {
                                            return "暴雨";
                                          } else if (params[0].data > 100) {
                                            return "大暴雨";
                                          }
                                        })()} </div>`;
      },
      backgroundColor: "#021D1F",
      extraCssText: "box-shadow: 0px 0px 12px 0px rgba(0,228,247,0.36);",
      borderColor: "rgba(0,228,247,0.36)",
      textStyle: {
        width: 100,
      },
    },
    series: [
      {
        data: props.echartsInfo.rainfallInfo,
        type: "line",
        smooth: true,
        showSymbol: false,
        itemStyle: {
          color: "#1FFACE",
        },
      },
    ],
  });
}
onMounted(() => {
  initEchart();
});
</script>
<style lang="scss" scoped>
.contain {
  &-rain {
    width: 400px;
    display: flex;
    &-place {
      display: flex;
      &-fixed {
        vertical-align: middle;
        width: 24px;
        height: 27px;
        margin-left: 5px;
      }
      &-position {
        margin-left: 12px;
        vertical-align: middle;
        font-size: 14px;
        color: #fff;
        font-family: PingFang SC;
        margin-top: 5px;
        padding-right: 16px;
        border-right: 2px solid #1fface;
      }
    }
    &-forecast {
      display: flex;
      margin-left: 19px;
      &-weatherIcon {
        width: 35px;
        height: 30px;
        vertical-align: middle;
      }
      &-weather {
        margin-left: 14px;
        color: #fff;
        vertical-align: middle;
        font-family: PingFang SC;
        font-size: 14px;
        margin-top: 5px;
        text-align: right;
      }
      &-temperature {
        margin-left: 19px;
        margin-top: 2px;
        vertical-align: middle;
        font-size: 18px;
        font-family: DIN;
        color: #1fface;
        text-align: right;
      }
    }
    &-information {
      width: 400px;
      height: 12px;
      grid-column: 1/3;
      text-align: right;
      font-size: 12px;
      margin-top: 6px;
      color: #1fface;
      font-family: PingFang SC;
    }
  }
  &-WaterEcharts {
    width: 430px;
    height: 150px;
  }
}
</style>
