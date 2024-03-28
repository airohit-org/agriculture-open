import request from '@/utils/request'

// 创建病虫害识别
export function createClassification(data) {
  return request({
    url: '/plant/classification/create',
    method: 'post',
    data: data
  })
}

// 获得病虫害识别
export function getClassificationDetail(data) {
  return request({
    url: '/plant/classification/get',
    method: 'get',
    params: data
  })
}

// 获取病虫害识别分页
export function getClassificationRecordsPage(data) {
  return request({
    url: '/plant/classification/page',
    method: 'get',
    params: data
  })
}

// 获取病虫害识别分页
export function deleteClassificationRecordsPage(data) {
  return request({
    url: '/plant/classification/delete',
    method: 'delete',
    params: data
  })
}