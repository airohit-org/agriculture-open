import {
  getFarmStatisticVo,
  getTaskInfoStatistic,
  warningMessageStatistic,
  getTodayWeather,
  getWeather10,
} from "@/api/overview";
import {
  formatFarmInfoList,
  formatTaskList,
  formatTodayWeather,
  formatWeather10,
  formatWarnList,
} from "./help";
import promiseObj from "@/utils/promiseObj";

const useOverviewStore = defineStore("overview", {
  state: () => ({
    plantArea: 0, // 种植面积
    farmInfoList: [], // 农场信息
    taskList: [], // 任务列表
    warnList: [], // 报警列表
    todayWeather: {}, // 今天天气
    weather10: {}, // 10日天气
  }),
  actions: {
    // 获取信息
    getOverviewInfo() {
      return promiseObj({
        farmStatisticVo: getFarmStatisticVo(),
        taskInfoStatistic: getTaskInfoStatistic(),
        messageStatistic: warningMessageStatistic({ limit: 20 }),
        todayWeather: getTodayWeather(),
        weather10: getWeather10(),
      }).then((res) => {
        const todayWeather = res.todayWeather?.value?.data || {};
        const weather10 = res.weather10?.value?.data || [];
        const taskList = res.taskInfoStatistic?.value?.data || [];
        const warnList = res.messageStatistic?.value?.data || {};
        const { crops, deviceCount, landCount, memberCount, plantArea } =
          res.farmStatisticVo?.value?.data || {};

        this.todayWeather = formatTodayWeather(todayWeather)
        this.weather10 = formatWeather10(weather10);
        this.plantArea = plantArea;
        this.taskList = formatTaskList(taskList);
        this.warnList = formatWarnList(warnList);
        this.farmInfoList = formatFarmInfoList({ deviceCount, landCount, memberCount, crops });
      });
    },
  }
})

export default useOverviewStore;