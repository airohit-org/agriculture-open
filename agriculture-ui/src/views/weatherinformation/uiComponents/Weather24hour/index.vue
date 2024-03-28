<template>
  <div>
    <OverviewTitle title="24小时预报" />
    <div class="contain">
      <div class="contain-weatherMenu">
        <div v-for="{ item, menuName } in weatherMenuInfo" :key="item">
          <div>{{ menuName }}</div>
        </div>
      </div>
      <div class="contain-details-wrapper">
        <div
          class="contain-details"
          v-for="{
            id,
            iconWeather,
            weather,
            temperature,
            temperatureUnit,
            humidity,
            humidityUnit,
            windDirection,
            windSpeed,
            windSpeedUnit,
            windScale,
            time,
          } in twentyFourHoursInfo"
          :key="id"
        >
          <div class="contain-details-top">
            <img class="contain-details-top-images" :src="iconWeather" />
            <div class="contain-details-top-weather">{{ weather }}</div>
            <div class="contain-details-top-temperature">
              {{ temperature }}{{ temperatureUnit }}
            </div>
            <div class="contain-details-top-humidity">
              {{ humidity }}{{ humidityUnit }}
            </div>
            <div class="contain-details-top-windDirection">
              {{ windDirection }}
            </div>
            <div class="contain-details-top-windSpeed">
              {{ windSpeed }}{{ windSpeedUnit }}
            </div>
            <div class="contain-details-top-windScale">{{ windScale }}</div>
          </div>
          <div class="contain-details-time">{{ time }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import OverviewTitle from "@/components/OverviewTitle/index.vue";
import weatherIcon from "@/assets/images/Overview/weather.png";

const props = defineProps({
  twentyFourHoursInfo: Array,
});
const weatherMenuInfo = [
  {
    item: 1,
    menuName: "湿度",
  },
  {
    item: 2,
    menuName: "风向",
  },
  {
    item: 3,
    menuName: "风速",
  },
];
</script>
<style lang="scss" scoped>
.contain {
  display: flex;
  width: 400px;
  font-size: 10px;
  color: #fff;

  ::-webkit-scrollbar {
    width: 4px;
    height: 4px;
  }

  ::-webkit-scrollbar-track {
    border-radius: 0px;
    background: rgba(255, 255, 255, 0.08);
  }

  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
    border-radius: 0px;
    background: linear-gradient(
      180deg,
      #1ee7e7 0%,
      rgba(30, 231, 231, 0.35) 100%
    );
  }

  &-weatherMenu {
    flex-shrink: 0;
    width: 50px;
    font-family: PingFang SC;
    text-align: center;
    margin-top: 87px;

    div {
      margin-top: 7px;
    }
  }
  &-details-wrapper {
    flex: 1;
    display: flex;
    overflow-x: auto;
  }

  &-details {
    flex-shrink: 0;
    width: 50px;
    text-align: center;
    font-family: DIN;
    margin-left: 2px;

    &-top {
      background: rgba(31, 250, 206, 0.08);
      &-images {
        width: 17px;
        height: 14px;
        margin-top: 10px;
      }

      &-weather {
        font-family: Adobe Heiti Std;
        margin-top: 7px;
      }

      &-temperature {
        color: #1fface;
        font-size: 14px;
        margin-top: 13px;
      }

      &-humidity {
        margin-top: 13px;
      }

      &-windDirection {
        margin-top: 7px;
      }

      &-windSpeed {
        margin-top: 7px;
      }

      &-windScale {
        margin-top: 8px;
        border-radius: 3px 3px 0px 0px;
        background-color: rgba(31, 250, 206, 0.6);
      }
    }

    &-time {
      margin-top: 10px;
    }
  }
}
</style>
