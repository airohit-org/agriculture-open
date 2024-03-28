import request from '@/utils/request'

// 创建农户
export function create(data) {
  return request({
    url: '/peasant/create',
    method: 'post',
    data: data
  })
}

// 更新农户
export function update(data) {
  return request({
    url: '/peasant/update',
    method: 'put',
    data: data
  })
}

// 删除农户
export function deleteUser(id) {
  return request({
    url: '/peasant/delete?id=' + id,
    method: 'delete'
  })
}

// 获得农户
export function get(id) {
  return request({
    url: '/peasant/get?id=' + id,
    method: 'get'
  })
}

// 获得农户分页
export function getPage(query) {
  return request({
    url: '/peasant/page',
    method: 'get',
    params: query
  })
}

// 导出农户 Excel
export function exportExcel(query) {
  return request({
    url: '/peasant/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
// 获得省
export function getProvinceList(query) {
  return request({
    url: '/system/district/getProvinceList',
    method: 'get',
    params: query
  })
}

// 根据省份编码获取城市
export function getCityByProvince(query) {
  return request({
    url: '/system/district/getCityByProvince',
    method: 'get',
    params: query
  })
}
// 根据城市编码获取
export function getAreaByCity(query) {
  return request({
    url: '/system/district/getAreaByCity',
    method: 'get',
    params: query
  })
}
// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/peasant/get-import-template',
    method: 'get',
    responseType: 'blob'
  })
}

//
export function allList() {
  return request({
    url: '/system/user/list-all-simple',
    method: 'get',
  })
}