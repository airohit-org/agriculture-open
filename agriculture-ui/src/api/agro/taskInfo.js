import request from '@/utils/request'

// 创建农事任务基本信息
export function createTaskInfo(data) {
  return request({
    url: '/plant/taskInfo/createTask',
    method: 'post',
    data: data
  })
}

// 更新农事任务基本信息
export function updateTaskInfo(data) {
  return request({
    url: '/plant/task-info/update',
    method: 'put',
    data: data
  })
}

// 删除农事任务基本信息
export function deleteTaskInfo(id) {
  return request({
    url: '/plant/taskInfo/deleteTaskInfo?id=' + id,
    method: 'delete'
  })
}

// 获得农事任务基本信息
export function getTaskInfo(id) {
  return request({
    url: '/plant/taskInfo/getTaskDetail?id=' + id,
    method: 'get'
  })
}

// 获得农事任务基本信息分页
export function getTaskInfoPage(query) {
  return request({
    url: '/plant/taskInfo/page',
    method: 'get',
    params: query
  })
}
// 获取日历页信息
export function getTaskInfoBaseVOListByDate(query) {
  return request({
    url:'/plant/taskInfo/getTaskInfoBaseVOListByDate',
    method: 'get',
    params: query
  })
}
// 导出农事任务基本信息 Excel
export function exportTaskInfoExcel(query) {
  return request({
    url: '/plant/task-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 指派任务
export function updateTaskAppoint(data) {
  return request({
    url: '/plant/taskInfo/updateTaskAppoint',
    method: 'put',
    data: data
  })
}
// 批量指派 
export function updateTaskAppointList(data) {
  return request({
    url: '/plant/taskInfo/updateTaskAppointList',
    method: 'put',
    data: data
  })
}
// 获取计划列表数据

export function getPlantList(query) {
  return request({
    url: '/plant/plan/list',
    method: 'get',
    params: query
  })
}
//获取地块绑定计划
export function getQueryPlanBindLand () {
  return request({
    url: '/plant/plan/queryPlanBindLand',
    method: 'get',
  })
}

export function getQueryLandPlan () {
  return request({
    url: '/plant/plan/queryLandPlan',
    method: 'get',
  })
}

export function getPlanTypeDataByPlantingPlanId (query) {
  return request({
    url: '/plant/plan-type-data/getPlanTypeDataByPlantingPlanId',
    method: 'get',
    params: query
  })
}
export function landBindPlan(query) {
  return request({
    url: '/plant/plan/landBindPlan',
    method: 'put',
    params: query
  })
}

export function typeChange(type)  {
  const typeList = [
    {
      label: "整地",
      value: "agro_task_raking",
      type: "4"
    },
    {
      label: "播种",
      value: "agro_task_seeding",
      type: "6"
    },
    {
      label: "施肥",
      value: "agro_task_fertilizer",
      type: "0"
    },        {
      label: "除草",
      value: "agro_task_weed",
      type: "7"
    },        {
      label: "中耕",
      value: "agro_task_intertill",
      type: "1"
    },        {
      label: "打药",
      value: "agro_task_pesticide",
      type: "3"
    },        {
      label: "灌溉",
      value: "agro_task_irrigation",
      type: "2"
    },        {
      label: "收割",
      value: "agro_task_reap",
      type: "5"
    }
]
  let name = ''
  typeList.forEach(item => {
    if(item.type == type) {
      name = item.value
    }
  })
  return name;
}

// 农事任务统计
export function getTaskInfoStatistic() {
  return request({
    url: '/statistics/getTaskInfoStatistic',
    method: 'get',
  })
}