<div align="center">
    <img src="https://img.shields.io/badge/Vue-3.3.9-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/Vite-5.0.4-green.svg"/>
    <img src="https://img.shields.io/badge/Element Plus-2.4.3-blue.svg"/>
    <img src="https://img.shields.io/badge/license-MIT-green.svg"/>
</div>

![](https://foruda.gitee.com/images/1708618984641188532/a7cca095_716974.png "rainbow.png")


## 系统简介

智农伴飞平台是基于 Vue3 + Vite5 + Element-Plus + Pinia 等主流技术栈构建的免费开源的智慧农业平台。通过传感器、监测器等设备采集农业生产的各种数据，包括土壤温度、湿度、光照强度、二氧化碳浓度、氧气浓度、降雨量等。通过对这些数据的分析，可以提供种植决策支持。


## 系统亮点

- **开箱即用**：基于 [RuoYi v3.8.7](https://github.com/yangzongzhuan/RuoYi-Vue3) 开发的智慧农业平台。

- **权限管理**：用户、角色、菜单、字典、部门等完善的权限系统功能。

- **基础设施**：动态路由、按钮权限、常用组件封装。

- **数据可视化**：Echarts、FullCalendar、Leaflet、Turf、Mqtt等。
  
- **特色功能**：地块智能划分管理、日历农事编排等。


## 运行环境

| 环境             | 名称版本 | 下载地址                                       |
| ---------------- | :------- | ---------------------------------------------- |
| **开发工具**     | VSCode   | [下载](https://code.visualstudio.com/Download) |
| **前端运行环境** | Node ≥20 | [下载](http://nodejs.cn/download)              |


## 核心功能

- **种植计划**：根据数据分析和市场需求，制定种植计划，包括品种、播种时间、施肥和灌溉等方面的内容。

- **地块可视化管理**：可视化地图地块划分，管理维护传感器设备信息、作物、农事活动、天启信息等。
  
- **气象概览**：展示当前天气状况，包括温度、湿度、光照强度、二氧化碳浓度、氧气浓度等。

## 使用须知
### 地图服务

- 地图服务采用天地图服务，请在使用前申请天地图服务key。
- 请在 `src/utils/chinatmsproviders.js` 文件中配置天地图服务key。
```json
// src/utils/chinatmsproviders.js
TianDiTu: {
    // ...,
    keys: [
        // 需要申请天地图服务key，配置在此处，可配置多个，随机取用
    ],
}
```

### 前端项目启动

```bash
# 进入项目目录
cd agriculture-ui

# 安装依赖
yarn --registry=https://registry.npmmirror.com

# 启动服务
yarn dev

# 前端访问地址 http://localhost:80
```

### 前端项目部署配置

```bash
#VITE配置

# 页面标题
VITE_APP_TITLE = 智农伴飞

# 环境配置 development开发环境 production生产环境
VITE_APP_ENV = 'production'

# 请求路径
VITE_APP_BASE_API = '/agriculture-open-api'

# 是否在打包时开启压缩，支持 gzip 和 brotli
VITE_BUILD_COMPRESS = gzip

```

### 前端项目编译

```bash
# 进入项目目录
cd agriculture-ui

# 构建生产环境
yarn build:prod

# 构建测试环境
# yarn build:stage

# 打包在 dist 目录下
# dist目录结构
dist/                             打包后的输出目录
│
├── assets/                       打包后的静态资源、javascript文件、css文件等
├── favicon.ico/                  网站logo图标
├── index.html/                   打包后的主HTML文件
└── index.html.gz/                压缩后的主HTML文件
```
通常情况下将 dist 文件夹的静态文件发布到你的 nginx 或者静态服务器即可，其中的 index.html 是后台服务的入口页面。

### 前端文件目录

``` 
agriculture-ui/
│
├── bin/                          批处理文件，打包、运行
├── public/                       公共文件，图标、html模板
├── src/                          前端相关源码
│   ├── api/                      应用模块接口
│   ├── assets/                   静态资源，图片、图标、logo
│   ├── components/               全局自定义封装组件，需要配置main.js
│   ├── directive/                全局标签
│   ├── layout/                   页面布局相关文件
│   ├── plugins/                  全局自定义封装方法
│   ├── router/                   路由相关
│   ├── store/                    属性相关
│   ├── utils/                    通用工具
│   ├── views/                    view相关页面实现代码
│   ├── App.vue                   入口页面
│   ├── main.js                   入口js，全局方法挂载等
│   ├── permission.js             权限管理控制，拉取系统权限
│   └── settings.js               全局系统配置，通用属性
│
├── .env.development              开发环境配置
├── .env.production               生产环境配置
├── .env.staging                  测试环境配置
├── .gitgnore                     Git相关忽略
├── index.html                    入口html
├── LICENSE                       开源协议
├── package.json                  配置依赖模块、封装启动命令等
├── README.md                     项目描述文件
└── vite.config.js                vite配置文件
```

### 前端技术栈

| 技术栈       | 描述                                   | 版本    | 官网地址                             |
| ------------ | -------------------------------------- | ------- | ------------------------------------ |
| Vue3         | 渐进式 JavaScript 框架                 | 3.3.9   | https://cn.vuejs.org/                |
| pinia        | 新一代状态管理工具                     | 2.1.7   | https://pinia.vuejs.org/             |
| vite         | 前端开发与构建工具                     | 5.0.4   | https://cn.vitejs.dev/               |
| Element Plus | 基于 Vue 3，面向设计师和开发者的组件库 | 2.4.3   | https://element-plus.gitee.io/zh-CN/ |
| Echarts      | 一个基于 JavaScript 的开源可视化图表库 | 5.4.3   | https://echarts.apache.org/zh/       |
| FullCalendar | jQuery 的日历插件                      | ^6.1.10 | https://fullcalendar.io/             |
| vue-router   | Vue.js 的官方路由                      | 4.2.5   | https://router.vuejs.org/zh/         |
| leaflet      | 交互式地图的开源 JavaScript 库         | ^1.9.4  | https://leafletjs.com/               |
| turf         | 模块化的 GIS 引擎                      | ^6.5.0  | https://turfjs.fenxianglu.cn/        |
| mqtt         | 机器对机器(M2M)/物联网(IoT)连接协议    | ^5.5.0  | https://mqtt.p2hp.com/               |


### 前端注意事项

- **项目启动浏览器访问空白**

  请升级浏览器尝试，低版本浏览器内核可能不支持某些新的 JavaScript 语法，比如可选链操作符 `?.`。

- **项目同步仓库更新升级**

  项目同步仓库更新升级之后，建议 `yarn install` 安装更新依赖之后启动 。

- **项目组件、函数和引用爆红**

	重启 VSCode 尝试

- **其他问题**

  如果有其他问题或者建议，欢迎[ISSUE](https://gitee.com/ai_5/agriculture-open/issues/new)




## 版权信息

待补充