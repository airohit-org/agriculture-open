import request from "@/utils/request";

// 分页
export function getInfoPage(query) {
  return request({
    url: "/device/info/page",
    method: "get",
    params: query,
  });
}

// 获得设备基本信息
export function getInfo(id) {
  return request({
    url: "/device/info/get?id=" + id,
    method: "get",
  });
}

// 查找土壤设备数据
export function getSoilDeviceDataDONew(query) {
  return request({
    url: "/device/info/getSoilDeviceDataDONew",
    method: "get",
    params: query,
  });
}
// 查找天气设备数据
export function getWeatherDeviceDataDONew(query) {
  return request({
    url: "/device/info/getWeatherDeviceDataDONew",
    method: "get",
    params: query,
  });
}

// 查找地块关联设备数据
export function getDeviceLandListVoList(query) {
  return request({
    url: "/device/info/getDeviceLandListVoList",
    method: "get",
    params: query,
  });
}


    // 查找设备分组统计数据
    export function getDeviceGroupVoList(query) {
      return request({
        url: '/device/info/getDeviceGroupVoList',
        method: 'get',
        params: query
      })
    }

    