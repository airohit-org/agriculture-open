<template>
  <div class="warn-one-info" v-show="show">
    <img :src="icon" alt="" />
    <span class="prev">{{ title }}</span>
    <div class="line"></div>
    <span class="next">{{ text }}</span>
    <i class="icon el-icon-close" @click="show = false" />
  </div>
</template>

<script setup>
const props = defineProps({
  icon: String,
  title: String,
})
</script>

<style lang="scss" scoped>
$scale: 1.5;

.warn-one-info {
  background: #0e0e0f;
  box-shadow: inset -4px -4px 6px rgba(255, 130, 46, 0.25),
    inset 4px 4px 6px rgba(255, 130, 46, 0.3);
  position: relative;
  display: flex;
  align-items: center;
  height: 27px * $scale;
  padding-left: 15px;

  img {
    width: 19px  * $scale;
    aspect-ratio: 1 / 1;
    margin-right: 11px;
  }

  .prev {
    font-size: 12px  * $scale;
    color: #fcf366;
  }

  .next {
    font-size: 12px  * $scale;
    color: #ffffff;
    padding-right: 44px;
  }

  .line {
    margin: 0 9px * $scale;;
    height: 14px;
    width: 1px;
    background-color: #fff;
  }

  .icon {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 13px;
    color: #fff;
  }
}
</style>
