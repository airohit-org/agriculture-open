import auth from '@/plugins/auth'
import router, { constantRoutes, dynamicRoutes } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'
import InnerLink from '@/layout/components/InnerLink'
import {toCamelCase} from "@/utils";

// 匹配views里面所有的.vue文件
const modules = import.meta.glob('./../../views/**/*.vue')

const usePermissionStore = defineStore(
  'permission',
  {
    state: () => ({
      routes: [],
      addRoutes: [],
      defaultRoutes: [],
      topbarRouters: [],
      sidebarRouters: []
    }),
    actions: {
      setRoutes(routes) {
        this.addRoutes = routes
        this.routes = constantRoutes.concat(routes)
      },
      setDefaultRoutes(routes) {
        this.defaultRoutes = constantRoutes.concat(routes)
      },
      setTopbarRoutes(routes) {
        this.topbarRouters = routes
      },
      setSidebarRouters(routes) {
        this.sidebarRouters = routes
      },
      generateRoutes(roles) {
        return new Promise(resolve => {
          // 向后端请求路由数据
          getRouters().then(res => {
            const sdata = JSON.parse(JSON.stringify(res.data))
            const rdata = JSON.parse(JSON.stringify(res.data))
            const defaultData = JSON.parse(JSON.stringify(res.data))
            const sidebarRoutes = filterAsyncRouter(sdata)
            const rewriteRoutes = filterAsyncRouter(rdata, false, true)
            const defaultRoutes = filterAsyncRouter(defaultData)
            const asyncRoutes = filterDynamicRoutes(dynamicRoutes)
            asyncRoutes.forEach(route => { router.addRoute(route) })
            this.setRoutes(rewriteRoutes)
            this.setSidebarRouters(constantRoutes.concat(sidebarRoutes))
            this.setDefaultRoutes(sidebarRoutes)
            this.setTopbarRoutes(defaultRoutes)
            resolve(rewriteRoutes)
          })
        })
      }
    }
  })

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
  return asyncRouterMap.filter(route => {
    // 将 ruoyi 后端原有耦合前端的逻辑，迁移到此处
    // 处理 meta 属性
    route.meta = {
      title: route.name,
      icon: route.icon,
      noCache: !route.keepAlive,
    }
    // 路由地址转首字母大写驼峰，作为路由名称，适配 keepAlive
    route.name = toCamelCase(route.path, true)
    // 处理三级及以上菜单路由缓存问题，将path名字赋值给name
    if (route.path.indexOf("/") !== -1) {
      const pathArr = route.path.split("/");
      route.name = toCamelCase(pathArr[pathArr.length - 1], true)
    }
    route.hidden = !route.visible
    // 处理 component 属性
    if (route.children) { // 父节点
      if (route.parentId === 0) {
        route.component = Layout
      } else {
        route.component = ParentView
      }
    } else { // 根节点
      route.component = loadView(route.component)
    }

    // filterChildren
    if (type && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, type)
    } else {
      delete route['children']
    }
    return true
  })
  // return asyncRouterMap.filter(route => {
  //   if (type && route.children) {
  //     route.children = filterChildren(route.children)
  //   }
  //   if (route.component) {
  //     // Layout ParentView 组件特殊处理
  //     if (route.component === 'Layout') {
  //       route.component = Layout
  //     } else if (route.component === 'ParentView') {
  //       route.component = ParentView
  //     } else if (route.component === 'InnerLink') {
  //       route.component = InnerLink
  //     } else {
  //       route.component = loadView(route.component)
  //     }
  //   }
  //   if (route.children != null && route.children && route.children.length) {
  //     route.children = filterAsyncRouter(route.children, route, type)
  //   } else {
  //     delete route['children']
  //     delete route['redirect']
  //   }
  //   return true
  // })
}

function filterChildren(childrenMap, lastRouter = false) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView' && !lastRouter) {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    if (lastRouter) {
      el.path = lastRouter.path + '/' + el.path
      if (el.children && el.children.length) {
        children = children.concat(filterChildren(el.children, el))
        return
      }
    }
    children = children.concat(el)
  })
  return children
}

// 动态路由遍历，验证是否具备权限
export function filterDynamicRoutes(routes) {
  const res = []
  routes.forEach(route => {
    if (route.permissions) {
      if (auth.hasPermiOr(route.permissions)) {
        res.push(route)
      }
    } else if (route.roles) {
      if (auth.hasRoleOr(route.roles)) {
        res.push(route)
      }
    }
  })
  return res
}

export const loadView = (view) => {
  let res;
  for (const path in modules) {
    const dir = path.split('views/')[1].split('.vue')[0];
    if (dir === view) {
      res = () => modules[path]();
    }
  }
  return res;
}

export default usePermissionStore
