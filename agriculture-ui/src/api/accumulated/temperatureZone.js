import request from '@/utils/request'

// 创建积温带管理
export function createTemperatureZone(data) {
  return request({
    url: '/land/temperature-zone/create',
    method: 'post',
    data: data
  })
}

// 更新积温带管理
export function updateTemperatureZone(data) {
  return request({
    url: 'land/temperature-zone/update',
    method: 'put',
    data: data
  })
}

// 删除积温带管理
export function deleteTemperatureZone(id) {
  return request({
    url: '/land/temperature-zone/delete?id=' + id,
    method: 'delete'
  })
}

// 获得积温带管理
export function getTemperatureZone(id) {
  return request({
    url: '/land/temperature-zone/get?id=' + id,
    method: 'get'
  })
}

// 获得积温带管理分页
export function getTemperatureZonePage(query) {
  return request({
    url: '/land/temperature-zone/page',
    method: 'get',
    params: query
  })
}

// 导出积温带管理 Excel
// export function exportTemperatureZoneExcel(query) {
//   return request({
//     url: '/accumulated/temperature-zone/export-excel',
//     method: 'get',
//     params: query,
//     responseType: 'blob'
//   })
// }
