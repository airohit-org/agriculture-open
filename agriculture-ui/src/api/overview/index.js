import request from '@/utils/request';

//获得农场信息概览 种植面积 员工数量 地块数量
export function getFarmStatisticVo(query) {
  return request({
    url: "/statistics/getFarmStatisticVo",
    method: "get",
    params: query,
  });
}

//   获得农事任务统计 状态名称 数量
export function getTaskInfoStatistic(query) {
  return request({
    url: "/statistics/getTaskInfoStatistic",
    method: "get",
    params: query,
  });
}

// 预警消息统计
export function warningMessageStatistic(query) {
  return request({
    url: "/statistics/warningMessageStatistic",
    method: "get",
    params: query,
  });
}

// 获得天气信息
export function getTodayWeather(query) {
  return request({
    url: "/weather/getTodayWeather",
    method: "get",
    params: query,
  });
}

// 获得10天气信息
export function getWeather10(query) {
  return request({
    url: "/weather/getWeather10",
    method: "get",
    params: query,
  });
}