import request from '@/utils/request';
export function getCropsPage(query) {
  return request({
    url: '/land/crops/page',
    method: 'get',
    params: query
  })
}

  // 获得品种管理分页
export function getVarietiesPage(query) {
  return request({
    url: '/land/varieties/page',
    method: 'get',
    params: query
  })
}