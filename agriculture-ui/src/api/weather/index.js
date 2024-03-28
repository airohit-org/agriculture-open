import request from "@/utils/request";

// 获得24小时预报
export function getWeather24Hour(query) {
  return request({
    url: "/weather/getWeather24Hour",
    method: "get",
    params: query,
  });
}

export function getWeatherRain(query) {
  return request({
    url: "/weather/getWeatherRain",
    method: "get",
    params: query,
  });
}

export function getWeatherDamage(query) {
  return request({
    url: "/weather/getWeatherDamage",
    method: "get",
    params: query,
  });
}

export function getWeatherRadar(query) {
  return request({
    url: "/weather/getWeatherRadar",
    method: "get",
    params: query,
  });
}

export function getWeatherGrid(query) {
  return request({
    url: "/weather/getWeatherGrid",
    method: "get",
    params: query,
  });
}
