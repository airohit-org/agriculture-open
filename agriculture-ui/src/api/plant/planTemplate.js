import request from '@/utils/request'

// 创建计划模版
export function createPlanTemplate(data) {
  return request({
    url: '/plant/plan-template/create',
    method: 'post',
    data: data
  })
}

// 更新计划模版
export function updatePlanTemplate(data) {
  return request({
    url: '/plant/plan-template/update',
    method: 'put',
    data: data
  })
}

// 删除计划模版
export function deletePlanTemplate(id) {
  return request({
    url: '/plant/plan-template/delete?id=' + id,
    method: 'delete'
  })
}

// 获得计划模版
export function getPlanTemplate(id) {
  return request({
    url: '/plant/plan-template/get?id=' + id,
    method: 'get'
  })
}

// 获得计划模版分页
export function getPlanTemplatePage(query) {
  return request({
    url: '/plant/plan-template/page',
    method: 'get',
    params: query
  })
}

// 导出计划模版 Excel
export function exportPlanTemplateExcel(query) {
  return request({
    url: '/plant/plan-template/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
