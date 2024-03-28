
## 技术栈

### 建议Node.js版本 >= v20

| 技术         | 版本    |
| ------------ | ------- |
| Vue3         | 3.3.9   |
| pinia        | 2.1.7   |
| vite         | 5.0.4   |
| Element Plus | 2.4.3   |
| Echarts      | 5.4.3   |
| FullCalendar | ^6.1.10 |
| vue-router   | 4.2.5   |
| leaflet      | ^1.9.4  |
| turf         | ^6.5.0  |
| mqtt         | ^5.5.0  |

## 前端运行

```bash
# 进入项目目录
cd agriculture-admin-ui-vue3

# 安装依赖
yarn --registry=https://registry.npmmirror.com

# 启动服务
yarn dev

# 构建测试环境 yarn build:stage
# 构建生产环境 yarn build:prod
# 前端访问地址 http://localhost:80
```

## 文件目录

```
agriculture-admin-ui-vue3/
│
├── bin/（批处理文件，打包、运行）
├── build/（构建相关）
├── public/（公共文件，图标、html模板）
├── src/（前端相关源码）
│   ├── api/（应用模块接口）
│   ├── assets/（静态资源，图片、图标、logo）
│   ├── components/（全局自定义封装组件，需要配置main.js）
│   ├── directive/（全局标签）
│   ├── layout/（页面布局相关文件）
│   ├── plugins/（全局自定义封装方法）
│   ├── router/（路由相关）
│   ├── store/（属性相关）
│   ├── utils/（通用工具）
│   ├── views/（view相关页面实现代码）
│   ├── App.vue（入口页面）
│   ├── main.js（入口js，全局方法挂载等）
│   ├── permission.js（权限管理控制，拉取系统权限）
│   └── settings.js（全局系统配置，通用属性）
│
├── .env.development（开发环境配置）
├── .env.production（生产环境配置）
├── .env.staging（测试环境配置）
├── .gitgnore（Git相关忽略）
├── index.html（入口html）
├── LICENSE（开源协议）
├── package.json（配置依赖模块、封装启动命令等）
├── README.md（描述文件）
└── vite.config.js（vite配置文件）
```