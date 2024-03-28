import request from '@/utils/request'

// 创建农事任务模版基本信息
export function createTaskTemplateInfo(data) {
  return request({
    url: '/agro/task-template-info/create',
    method: 'post',
    data: data
  })
}

// 更新农事任务模版基本信息
export function updateTaskTemplateInfo(data) {
  return request({
    url: '/agro/task-template-info/update',
    method: 'put',
    data: data
  })
}

// 更新农事任务基本信息
export function updateTaskDetail(data) {
  return request({
    url: '/plant/taskInfo/updateTaskDetail',
    method: 'put',
    data: data
  })
}

// 删除农事任务模版基本信息
export function deleteTaskTemplateInfo(id) {
  return request({
    url: '/agro/task-template-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得农事任务模版基本信息
export function getTaskTemplateInfo(id) {
  return request({
    url: '/agro/task-template-info/get?id=' + id,
    method: 'get'
  })
}

// 获得农事任务模版基本信息分页
export function getTaskTemplateInfoPage(query) {
  return request({
    url: '/agro/task-template-info/page',
    method: 'get',
    params: query
  })
}

// 导出农事任务模版基本信息 Excel
export function exportTaskTemplateInfoExcel(query) {
  return request({
    url: '/agro/task-template-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
// 获得农事任务提交表单模版
export function getTaskFiled(query) {
  return request({
    url: '/plant/taskInfo/getTaskFiled',
    method: 'get',
    params: query
  })
}
//创建农事任务
export function createTask(data) {
  return request({
    url: '/plant/taskInfo/createTask',
    method: 'post',
    data: data
  })
}

// 获取所有种植计划

export function allPlantList() {
  return request({
    url: '/plant/plan/allList',
    method: "get"
  })
}