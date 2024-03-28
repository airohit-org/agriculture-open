<template>
  <div class="map-right-layout">
    <div class="top-0">
      <slot name="top-0"></slot>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.map-right-layout {
  position: absolute;
  bottom: 100px;
  width: auto;
  height: auto;
  z-index: 999;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;

  .top {
    &-0 {
      height: 50px;
      width: 186px;
    }
  }
}
</style>
