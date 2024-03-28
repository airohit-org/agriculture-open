
import { getIp } from '@/utils/auth';
import request from '@/utils/request';

const formatGaode = ({rectangle}) => {
  return rectangle.split(';').map(item => {
    return item.split(',').reverse()
  });
}
// 创建地块信息
export function createLand(data) {
  return request({
    url: '/land/create',
    method: 'post',
    data: data
  })
}

export const ipLocate = () => {
  const ip = getIp();
  return fetch(`https://restapi.amap.com/v3/ip?ip=${ip}&key=c446f0a655ac7522840bd93344d339fd`)
    .then(res => res.json()).then(formatGaode)
}

// 更新地块信息
export function updateLand(data) {
  return request({
    url: '/land/update',
    method: 'put',
    data: data
  })
}

// 删除地块信息
export function deleteLand(id) {
  return request({
    url: '/land/delete?id=' + id,
    method: 'delete'
  })
}

// 获得地块信息
export function getLand(id) {
  return request({
    url: '/land/get?id=' + id,
    method: 'get'
  })
}

// 获得地块信息分页
export function getLandPage(query) {
  return request({
    url: '/land/page',
    method: 'get',
    params: query
  })
}

// 导出地块信息 Excel
export function exportLandExcel(query) {
  return request({
    url: '/land/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
