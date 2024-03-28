import { getWeather24Hour, getWeatherRain, getWeatherDamage, getWeatherRadar, getWeatherGrid } from "@/api/weather";
import { getDeviceLandListVoList } from '@/api/device/info';
import { getTodayWeather, getWeather10 } from "@/api/overview";
import promiseObj from "@/utils/promiseObj";
import { formatRainFallInfo, formatMsgList, formatTimeDateInfo, formatWeather24, formatRadar, formatDamage, formatGrid, formatFarmDevice } from "./help";
import { formatTodayWeather, formatWeather10 } from "../overview/help";

const useWeatherStore = defineStore('weather', {
  namespaced: true,
  state: () => ({
    weather24: [],
    farmDevice: [],
    timeDataInfo: [],
    rainfallInfo: [],
    msgList: [],
    radarInfo: [],
    showIcons: [],
    grid: {},
    initGridId: 0,
    today: {},
    weather10: {}, // 10日天气
  }),
  actions: {
    // 获取信息
    getWeatherInfo() {
      return promiseObj({
        weather24: getWeather24Hour(),
        rain: getWeatherRain(),
        today: getTodayWeather(),
        weather10: getWeather10(),
        damage: getWeatherDamage(),
        radar: getWeatherRadar(),
        farmDevice: getDeviceLandListVoList({ deviceId: 52 }),
        grid: getWeatherGrid()
      }).then((res) => {
        console.log(res)
        const weather10 = res.weather10?.value?.data || [];
        const weather24 = res.weather24?.value?.data || [];
        const rain = res.rain?.value?.data || [];
        const todayWeather = res.today?.value?.data || {};
        const radar = res.radar?.value?.data || {};
        const damage = res.damage?.value?.data || {};
        const grid = res.grid?.value?.data || {};
        const farmDevice = res.farmDevice?.value?.data || {};
        this.grid = formatGrid(grid);
        this.farmDevice = formatFarmDevice(farmDevice);
        this.initGridId = grid?.[0].landId;
        this.radarInfo = formatRadar(radar);
        this.showIcons = formatDamage(damage);
        formatRainFallInfo(rain) && (this.rainfallInfo = formatRainFallInfo(rain));
        this.weather10 = formatWeather10(weather10);
        formatTimeDateInfo(rain) && (this.timeDataInfo = formatTimeDateInfo(rain));
        formatMsgList(rain) && (this.msgList = formatMsgList(rain));
        formatTodayWeather(todayWeather) && (this.today = formatTodayWeather(todayWeather));
        formatWeather24(weather24) && (this.weather24 = formatWeather24(weather24));
      });
    },
  },
});

export default useWeatherStore;