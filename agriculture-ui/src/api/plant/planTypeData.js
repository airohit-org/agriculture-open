import request from '@/utils/request'

// 创建种植计划类型
export function createPlanTypeData(data) {
  return request({
    url: '/plant/plan-type-data/create',
    method: 'post',
    data: data
  })
}

// 更新种植计划类型
export function updatePlanTypeData(data) {
  return request({
    url: '/plant/plan-type-data/update',
    method: 'put',
    data: data
  })
}

// 删除种植计划类型
export function deletePlanTypeData(id) {
  return request({
    url: '/plant/plan-type-data/delete?id=' + id,
    method: 'delete'
  })
}

// 获得种植计划类型
export function getPlanTypeData(id) {
  return request({
    url: '/plant/plan-type-data/get?id=' + id,
    method: 'get'
  })
}

// 获得种植计划类型分页
export function getPlanTypeDataPage(query) {
  return request({
    url: '/plant/plan-type-data/page',
    method: 'get',
    params: query
  })
}

// 导出种植计划类型 Excel
export function exportPlanTypeDataExcel(query) {
  return request({
    url: '/plant/plan-type-data/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 获得种植计划类型分页
export function getPlanTypeDataByPlantingPlanId(query) {
  return request({
    url: '/plant/plan-type-data/getPlanTypeDataByPlantingPlanId',
    method: 'get',
    params: query
  })
}
