import request from '@/utils/request'

// 创建种植计划
export function createPlan(data) {
  return request({
    url: '/plant/plan/create',
    method: 'post',
    data: data
  })
}

// 更新种植计划
export function updatePlan(data) {
  return request({
    url: '/plant/plan/update',
    method: 'put',
    data: data
  })
}

// 删除种植计划
export function deletePlan(id) {
  return request({
    url: '/plant/plan/delete?id=' + id,
    method: 'delete'
  })
}

// 获得种植计划
export function getPlan(id) {
  return request({
    url: '/plant/plan/get?id=' + id,
    method: 'get'
  })
}

// 获得种植计划分页
export function getPlanPage(query) {
  return request({
    url: '/plant/plan/page',
    method: 'get',
    params: query
  })
}

// 导出种植计划 Excel
export function exportPlanExcel(query) {
  return request({
    url: '/plant/plan/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
// 获得种植计划分页
export function dicttype(query) {
  return request({
    url: '/system/dict-type/page',
    method: 'get',
    params: query
  })
}
// 获得种植计划分页
export function dictdata(query) {
  return request({
    url: '/system/dict-data/page',
    method: 'get',
    params: query
  })
}

// 克隆种植计划
export function clonePlan(data) {
  return request({
    url: '/plant/plan/clone',
    method: 'post',
    data: data
  })
}
// 克隆模版
export function cloneTemplate(data) {
  return request({
    url: '/plant/plan/cloneTemplate',
    method: 'post',
    data: data
  })
}

// 种植计划发布或取消发布
export function planPublish(data) {
  return request({
    url: '/plant/plan/planPublish',
    method: 'put',
    params: data
    // data: data
  })
}