<template>
  <div :class="{ 'has-logo': showLogo }" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar :class="sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
        :text-color="sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
        :unique-opened="true"
        :active-text-color="theme"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="(route, index) in sidebarRouters"
          :key="route.path + index"
          :item="route"
          :base-path="route.path"
          class="bg"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/assets/styles/variables.module.scss'
import useAppStore from '@/store/modules/app'
import useSettingsStore from '@/store/modules/settings'
import usePermissionStore from '@/store/modules/permission'
import { onMounted } from 'vue'

const route = useRoute();
const appStore = useAppStore()
const settingsStore = useSettingsStore()
const permissionStore = usePermissionStore()

const sidebarRouters =  computed(() => permissionStore.sidebarRouters);
const showLogo = computed(() => settingsStore.sidebarLogo);
const sideTheme = computed(() => settingsStore.sideTheme);
const theme = computed(() => settingsStore.theme);
const isCollapse = computed(() => !appStore.sidebar.opened);

const activeMenu = computed(() => {
  const { meta, path } = route;
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu;
  }
  return path;
})
const handleIcon = ()=> {
      sidebarRouters.value.forEach(item => {
            //  农场概览展示
          if(item?.redirect) {
              if (route.path.split('/')[1] == 'index') {
                if(item?.children[0]?.meta?.icon.split('2').length <= 1 ) {
                    item.children[0].meta.icon = item.children[0].meta.icon + '2'
                    item.isNest = false
                } else {
                  
                }
              } else {
                if(item?.children[0]?.meta?.icon.split('2').length > 1 ) {
                  item.children[0].meta.icon = item.children[0].meta.icon.split("").slice(0, -1).join("")
                  item.isNest = true
                }
              }
          }

          if(item?.path == '/chat') {
            if (route.path.split('/')[1] == 'chat') {
              if(item?.children[0]?.meta?.icon.split('2').length <= 1 ) {
                    item.children[0].meta.icon = item.children[0].meta.icon + '2'
                    item.isNest = false
                } else {
                  
                }
              } else {
                if(item?.children[0]?.meta?.icon.split('2').length > 1 ) {
                  item.children[0].meta.icon = item.children[0].meta.icon.split("").slice(0, -1).join("")
                  item.isNest = true
                }
              }
          }

          // 菜单展示
            if(item?.meta?.icon) {
              if (item?.path.split('/')[1] == route.path.split('/')[1]) {
                  if(item.icon.split('2').length <= 1 || item.meta.icon.split('2').length <= 1) {
                    item.icon = item.icon + '2'
                    item.meta.icon = item.meta.icon + '2'
                  }
              }
              else {
                  if(item.icon.split('2').length > 1 || item.meta.icon.split('2').length > 1) {
                    item.icon = item.icon.split("").slice(0, -1).join("")
                    item.meta.icon = item.meta.icon.split("").slice(0, -1).join("")
                  }
              }
            }
          })
    }
watch(route, (newRoute) => {
  handleIcon()
}, { immediate: true });

onMounted(() => {
  handleIcon()
})
</script>
<style lang="scss" scoped>
.bg {
  background-color: #141B1F !important;
}

</style>