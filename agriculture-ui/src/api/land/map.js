import request from '@/utils/request'

export function getLands(query) {
  return request({
    url: '/land/list',
    method: 'get',
    query
  })
}
export function listAll(query) {
  return request({
    url: '/land/listAll',
    method: 'get',
    query
  })
}
// 获得地块信息分页
export function queryRaiseCrops() {
  return request({
    url: '/land/queryRaiseCrops',
    method: 'get',
  })
}

export function createLand(data) {
  return request({
    url: '/land/create',
    method: 'post',
    data
  })
}

export function getLandPage(params) {
  return request({
    url: '/land/page',
    method: 'get',
    params
  })
}
