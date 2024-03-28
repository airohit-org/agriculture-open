<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img :src="logoImg" class="sidebar-logo" />
        <!-- <h1 v-else class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{ title }} </h1> -->
        <!-- <h1 v-else class="sidebar-title" style=" color: #00ffde">{{ title }} </h1> -->
        <!-- <img v-else :src="title" class="sidebar-title" /> -->
        <img :src="logoBorder" class="sidebar-logoBorder" />
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img :src="logoImg" class="sidebar-logo" />
        <!-- <h1 class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.logoTitleColor : variables.logoLightTitleColor }">{{ title }} </h1> -->
        <!-- <h1 class="sidebar-title" style=" color: #00ffde">{{ title }} </h1> -->
        <img :src="logoTitle" class="sidebar-title" />
        <img :src="logoBorder" class="sidebar-logoBorder" />
      </router-link>
    </transition>
  </div>
</template>

<script setup>
import logoImg from '@/assets/logo/newLogo.png'
import logoTitle from '@/assets/logo/LogoTitle.png'
import logoBorder from '@/assets/logo/logoBorder.png'
import useSettingsStore from '@/store/modules/settings'
import variables from '@/assets/styles/variables.module.scss'
defineProps({
  collapse: {
    type: Boolean,
    required: true
  }
})

const title = import.meta.env.VITE_APP_TITLE;
const settingsStore = useSettingsStore();
const sideTheme = computed(() => settingsStore.sideTheme);
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
  // clip-path: polygon(0% 100%, 10% 0%, 100% 0%, 90% 100%);
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 91px;
  line-height: 50px;
  background: #1d1f35;
  // background: linear-gradient(270deg, rgba(0,255,246,0.11) 0%, rgba(0,255,251,0.04) 100%) !important;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 55px;
    width: 100%;
    margin-top: 10px;
    // background: linear-gradient(270deg, rgba(0,255,246,0.11) 0%, rgba(0,255,251,0.04) 100%);

    & .sidebar-logo {
      width: 57px;
      vertical-align: middle;
      margin-right: 12px;
      margin: 0;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      vertical-align: middle;
      width: 134px;
      height: 57px;
      margin-right: 5px;
    }

    & .sidebar-logoBorder{
      width: 100%;
      height: 36px;
      position: absolute;
      top: 50px;
      left: 0;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
