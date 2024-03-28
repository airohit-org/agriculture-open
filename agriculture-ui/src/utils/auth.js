import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return localStorage.getItem(TokenKey)
}

export function setToken(token) {
  return localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  return localStorage.removeItem(TokenKey)
}


// ========== 租户相关 ==========

const TenantIdKey = 'TENANT_ID'
const TenantNameKey = 'TENANT_NAME'
const FarmTenantId = 'farm-tenant-id'

export function getFarmTenantId() {
  return localStorage.getItem(FarmTenantId)
}

export function setFarmTenantId(val) {
  localStorage.setItem(FarmTenantId, val)
}

export function removeFarmTenantId() {
  localStorage.removeItem(FarmTenantId)
}

export function getTenantName() {
  return localStorage.getItem(TenantNameKey)
}

export function setTenantName(username) {
  localStorage.setItem(TenantNameKey, username)
}

export function removeTenantName() {
  localStorage.removeItem(TenantNameKey)
}

export function getTenantId() {
  return localStorage.getItem(TenantIdKey)
}

export function setTenantId(username) {
  localStorage.setItem(TenantIdKey, username)
}

export function removeTenantId() {
  localStorage.removeItem(TenantIdKey)
}

const IPKey = 'IPKey'

export function setIp(ip) {
  localStorage.setItem(IPKey, ip)
}

export function getIp() {
  return localStorage.getItem(IPKey)
}