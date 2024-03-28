import request from '@/utils/request'


// 获得山东仁科设备全部信息分页
export function getDeviceInfoPage(query) {
  return request({
    url: '/device/obs/getUserDevice',
    method: 'get',
    params: query
  })
}

// 获得设备全部信息
export function getUserDeviceList(query) {
  return request({
    url: '/device/info/getUserDeviceList',
    method: 'get',
    params: query
  })
}

// 获得设备分类
export function getUserClassify(query) {
  return request({
    url: '/device/obs/getUserClassify',
    method: 'get',
    params: query
  })
}

// 创建设备信息
export function claimDevice(data) {
  return request({
    url: '/device/obs/claimDevice',
    method: 'post',
    data: data
  })
}

// 更新设备信息
export function updateDevice(data) {
  return request({
    url: '/device/obs/updateDevice',
    method: 'post',
    data: data
  })
}
// 删除设备信息
export function deleteDevice(id) {
  return request({
    url: '/device/obs/deleteDevice?id=' + id,
    method: 'get'
  })
}
// 获得设备实时数据
export function getRealTimeData(query) {
  return request({
    url: '/device/obs/getRealTimeData',
    method: 'get',
    params: query
  })
}


// 获得设备厂商
export function getFirm(query) {
  return request({
    url: '/device/obs/getFirm',
    method: 'get',
    params: query
  })
}

// 查看设备详情
export function getOne(query) {
  return request({
    url: '/device/obs/getOne',
    method: 'get',
    params: query
  })
}

// 查看设备类型
export function deviceType(query) {
  return request({
    url: '/device/obs/deviceType',
    method: 'get',
    params: query
  })
}

// 自动获取ip端口模板
export function getIpAndPort(query) {
  return request({
    url: '/device/obs/getIpAndPort',
    method: 'get',
    params: query
  })
}


// 查询北京天航设备数据详情
export function queryBeijingTHDeviceInfoByDeviceId(query) {
  return request({
    url: '/device/obs/queryBeijingTHDeviceInfoByDeviceId',
    method: 'get',
    params: query
  })
}
// 查看设备图片数据
export function getInfoImage(query) {
  return request({
    url: '/device/obs/getRealTimeDataImage',
    method: 'get',
    params: query
  })
}

// 查看设备图片数据
export function queryBeijingTHDeviceInfo(query) {
  return request({
    url: '/device/obs/queryBeijingTHDeviceInfo',
    method: 'get',
    params: query
  })
}