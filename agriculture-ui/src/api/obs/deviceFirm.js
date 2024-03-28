import request from '@/utils/request'

// 创建设备厂商信息
export function createDeviceFirm(data) {
  return request({
    url: '/device/firm/create',
    method: 'post',
    data: data
  })
}

// 更新设备厂商信息
export function updateDeviceFirm(data) {
  return request({
    url: '/device/firm/update',
    method: 'put',
    data: data
  })
}

// 删除设备厂商信息
export function deleteDeviceFirm(id) {
  return request({
    url: '/device/firm/delete?id=' + id,
    method: 'delete'
  })
}

// 获得设备厂商信息
export function getDeviceFirm(id) {
  return request({
    url: '/device/firm/get?id=' + id,
    method: 'get'
  })
}

// 获得设备厂商信息分页
export function getDeviceFirmPage(query) {
  return request({
    url: '/device/firm/page',
    method: 'get',
    params: query
  })
}

// 导出设备厂商信息 Excel
export function exportDeviceFirmExcel(query) {
  return request({
    url: '/device/firm/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

//初始化厂商信息
export function firmInit(query) {
  return request({
    url: '/device/firm/init',
    method: 'get',
    params: query
  })
}

//获得集团端厂商表
export function getFirm(query) {
  return request({
    url: '/device/firm/getFirm',
    method: 'get',
    params: query
  })
}