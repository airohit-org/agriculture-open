<template>
  <div>
    <OverviewTitle title="天气预报" />
    <div class="contain-overview">
      <div class="contain-overview-place">
        <img
          class="contain-overview-place-fixed"
          src="https://oss.airoteach.cn/7d4f30cb142ab93b8ff4016a71d0eacd75d75fb4ecb55d367c83464f44f90a05.png"
        />
        <div class="contain-overview-place-position">{{ place }}</div>
      </div>
      <div class="contain-overview-forecast">
        <img class="contain-overview-forecast-weatherIcon" :src="iconWeather" />
        <div class="contain-overview-forecast-weather">{{ weather }}</div>
        <div class="contain-overview-forecast-temperature">
          {{ todayMinTemperature }}℃~{{ todayMaxTemperature }}℃
        </div>
      </div>
    </div>
    <div class="contain-title"></div>
    <div class="contain-echarts" id="weatherEcharts" ref="echartsRef"></div>
    <div class="contain-classify">
      <div
        class="contain-classify-all"
        v-for="{ item, image, text, value, unit } in weatherInfo"
        :key="item"
      >
        <div class="contain-classify-all-image">
          <!-- <div></div> -->
          <img class="contain-classify-all-image-bg" alt="" />
          <img
            :class="`contain-classify-all-image-items-${item}`"
            class="contain-classify-all-image-items"
          />
        </div>
        <div class="contain-classify-all-name">{{ text }}</div>
        <div class="contain-classify-all-case">
          <span class="contain-classify-all-case-data">{{ value }}</span>
          <span class="contain-classify-all-case-unit">{{ unit }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import * as echarts from "echarts";
import { createOptionEcharts } from "./help";
import { ref, onMounted, watch, nextTick } from "vue";

const props = defineProps({
  place: String,
  weather: String,
  todayMaxTemperature: Number | String,
  todayMinTemperature: Number | String,
  weatherInfo: Array,
  iconWeather: String,
  echartsInfo: Object,
})

const echartsRef = ref()
const weatherEcharts = ref()

const getWeather = () => {
  !weatherEcharts.value && (
    nextTick(()=>{
      weatherEcharts.value = echarts.init(echartsRef.value)
    })
  )
}

function setOptions() {
  getWeather();
  const { dataTime, maximumTemperature, minimumTemperature } = props.echartsInfo;
  const option = createOptionEcharts(
    dataTime,
    maximumTemperature,
    minimumTemperature
  );
  weatherEcharts?.value.setOption(option);
}

onMounted(() => {
  getWeather()
})

watch(
  () => props.echartsInfo, 
  ()=>{
    setOptions()
  }, 
  {deep: true}
)
</script>

<style lang="scss" scoped>
.contain-overview {
  width: 425px;
  display: grid;
  margin-top: 31.5px;

  &-place {
    grid-column: 1/2;
    display: grid;

    &-fixed {
      width: 28px;
      height: 30px;
      grid-column: 1/2;
      vertical-align: middle;
      margin-left: 10px;
      animation: rotate360 4s linear infinite;
    }

    &-position {
      height: 28px;
      font-size: 19px;
      color: white;
      font-family: PingFang SC-Regular, PingFang SC;
      grid-column: 2/5;
      vertical-align: middle;
      padding-right: 20px;
      border-right: 2px solid #1fac8f;
    }
  }

  &-forecast {
    grid-column: 2/6;
    margin-left: 30px;
    display: grid;
    margin-bottom: 5px;

    &-weatherIcon {
      width: 39px;
      height: 30px;
      grid-column: 1/2;
      vertical-align: middle;
    }

    &-weather {
      font-size: 19px;
      font-family: PingFang SC-Regular, PingFang SC;
      color: white;
      grid-column: 2/4;
      vertical-align: middle;
    }

    &-temperature {
      height: 38px;
      font-size: 19px;
      font-family: DIN Alternate-Bold, DIN Alternate;
      font-weight: bold;
      color: #1fface;
      grid-column: 4/7;
      vertical-align: middle;
    }
  }
}

.contain-title {
  margin-top: 30.5px;
  width: 425px;
  height: 30px;
  background-image: url("https://oss.airoteach.cn/4545e6b3c68a80dcc074b47d813af2b528965b3de12f16e15aafbc8a11d25c70.png");
  background-size: cover;
  position: absolute;
}

.contain-echarts {
  margin-top: 30.5px;
  height: 245.72px;
  width: 405px;
}

.contain-classify {
  width: 400px;
  display: grid;
  grid-template-columns: repeat(2, auto);
  margin-top: 28.2px;

  &-all {
    width: 184px;
    height: 66px;
    display: grid;
    place-items: center;
    margin-top: 15px;
    background-image: url("https://oss.airoteach.cn/ab9d255025e3d7283539b8e830e9ffd563fb67f24713463dc1758aa29528377a.png");
    background-size: cover;
    transition: background-image ease 0.2s;

    &-image {
      grid-row: 1/4;
      grid-column: 1/2;
      width: 48px;
      height: 48px;
      position: relative;
      // background-image: url('https://oss.airoteach.cn/9e712afb45cb09574249c4f29b831daa5ceb82d6da9dea93b93eb6c71bc7f0a3.png');
      // background-size: cover;
      display: grid;
      place-items: center;

      &-bg {
        content: url("https://oss.airoteach.cn/470403b6abe9e953a9787b915537b997059f4a8945265421c01138f88981b4c4.png");
        width: 48px;
        height: 48px;
        position: absolute;
        left: 0;
        top: 0;
        animation: rotate infinite 20s linear;
      }

      &-items {
        width: 36px;
        height: 36px;
        &-1 {
          content: url("https://oss.airoteach.cn/9d173a09f5ddedac60e7eb596ce6dc5520a40a8fc9eff53b63a6786dfb6f1f12.png");
        }
        &-2 {
          content: url("https://oss.airoteach.cn/c70390db7a15972ece052411c3f26978560bcf71cad3e7dbfc1e2f22f0b42b6f.png");
        }
        &-3 {
          content: url("https://oss.airoteach.cn/f4571bd9077cc6868c6a37e3c07a215df441a088ff539911320cf22a35880646.png");
        }
        &-4 {
          content: url("https://oss.airoteach.cn/5be92989a2273d8330022546616ffeb743cdf7f7b3dae2b3675dbb1919dceb0a.png");
        }
        &-5 {
          content: url("https://oss.airoteach.cn/bf1738a171cb0ea5e5c697f9d321450db7c13126dc089a510e5432c228ce5219.png");
        }
        &-6 {
          content: url("https://oss.airoteach.cn/2882d1f57910922f866509a695f90f93ac34c282990fdf0df826ab05bc87161b.png");
        }
      }
    }

    &-name {
      grid-row: 1/2;
      grid-column: 2/3;
      width: 104px;
      color: white;
      margin-top: 5px;
      font-size: 14px;
    }

    &-case {
      grid-row: 2/4;
      grid-column: 2/3;
      width: 94px;
      margin-bottom: 5px;
      color: #1fface;

      &-data {
        font-size: 24px;
        white-space: nowrap;
      }

      &-unit {
        margin-left: 5px;
        font-size: 14px;
      }
    }
    &:hover {
      background-image: url("https://oss.airoteach.cn/a9de4b06cf8c83b9524f5b32c1938f0d2cec72c36526ca46b07dc16c9391604f.png");
      .contain-classify {
        &-all {
          &-case {
            color: #fcf366;
          }

          &-image {
            grid-row: 1/4;
            grid-column: 1/2;
            width: 48px;
            height: 48px;
            position: relative;
            display: grid;
            place-items: center;

            &-bg {
              content: url("https://oss.airoteach.cn/698a39edfab553a08f93077bd40ec8e226eb036298634a2c5bebc5152889fad8.png");
            }

            &-items {
              &-1 {
                content: url("https://oss.airoteach.cn/335aa2e6e1eec99c1a1e5fd3cc87ae4f57c3e1ac21cc80c1a48c0fc1e7e27fa7.png");
              }
              &-2 {
                content: url("https://oss.airoteach.cn/b6b147955f22f21a9baaba65703c9d8c2aa40ed4dc1dea984f4a44a6a63e86c7.png");
              }
              &-3 {
                content: url("https://oss.airoteach.cn/a027a916d51e9a984281fdc35f8ac5f476352ef0e96973909ee5682f269b1a59.png");
              }
              &-4 {
                content: url("https://oss.airoteach.cn/62c88f080ed5f37ae54cac45c754145e7183f8ee30a830df57a3cec6ef248dda.png");
              }
              &-5 {
                content: url("https://oss.airoteach.cn/1de3f764b8b0c2e27e6d7a2823383a042f8ed80fe78fa90c1ef6b4d9b79860e4.png");
              }
              &-6 {
                content: url("https://oss.airoteach.cn/58bccf5848bc080fffea268a94d9a8ad0ad31e78921b50d4183d56d0549df58d.png");
              }
            }
          }
        }
      }
    }
  }

  &-all:first-child,
  &-all:nth-child(2) {
    margin-top: 0px;
  }
}
@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

@keyframes rotate360 {
  0% {
    transform: rotateY(0deg);
  }

  20%,
  100% {
    transform: rotateY(360deg);
  }
}
</style>
