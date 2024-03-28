<template>
  <div class="btm-tools">
    <img v-for="({icon, type}) in list" :key="type" :src="icon" @click="$emit('clickTool', type)" />
  </div>
</template>

<script setup>
const props = defineProps({
  list: {
    type: Array,
    default: () => []
  }
});
</script>

<style lang="scss" scoped>
.btm-tools {
  display: flex;
  flex-direction: column;

  img {
    width: 37px;
    aspect-ratio: 1 / 1;
    margin-bottom: 13.88px;
  }
}
</style>
