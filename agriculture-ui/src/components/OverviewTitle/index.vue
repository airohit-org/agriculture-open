https://oss.airoteach.cn/1de3f764b8b0c2e27e6d7a2823383a042f8ed80fe78fa90c1ef6b4d9b79860e4.png<template>
  <div class="overview-title">
    <span>{{ title }}</span>
    <img src="https://oss.airoteach.cn/a524755cf7fce4a18a6b85889b8505311e84dc056afc59326dfc3bfae65d6de5.png" alt="">
    <div class="light"></div>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true,
  }
})
</script>

<style scoped lang="scss">
@import "./index.scss";
</style>
