import request from '@/utils/request'

// 创建农场
export function create(data) {
  return request({
    url: 'system/farm//create',
    method: 'post',
    data: data
  })
}

// 更新农场
export function update(data) {
  return request({
    url: 'system/farm//update',
    method: 'put',
    data: data
  })
}

// 删除农场
export function deleteFarm(id) {
  return request({
    url: 'system/farm//delete?id=' + id,
    method: 'delete'
  })
}

// 获得农场
export function get(id) {
  return request({
    url: 'system/farm//get?id=' + id,
    method: 'get'
  })
}

// 获得农场分页
export function getPage(query) {
  return request({
    url: 'system/farm//page',
    method: 'get',
    params: query
  })
}

// 导出农场 Excel
export function exportExcel(query) {
  return request({
    url: 'system/farm//export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 根据租户id获取农场信息
export function getFarmByTenant(params) {
  return request({
    url: '/system/farm/getFarmByTenant',
    method: 'get',
    params
  })
}

// 根据租户id获取农场信息
export function getFarmListByTenant(params) {
  return request({
    url: '/system/farm/getFarmListByTenant',
    method: 'get',
    params
  })
}

export function getFarmList() {
  return request({
    url:'system/farm/farmList',
    method: 'get',
  })
}
