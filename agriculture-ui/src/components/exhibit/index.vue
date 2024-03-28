<template>
  <div class="ExhibitItem">
    <ExhibitItem
      v-for="(item, index) in list"
      :color="colors[index % colors.length]"
      :key="item.prompt + item.title"
      :imgSrc="item.imgSrc"
      :title="item.title"
      :prompt="item.prompt"
    ></ExhibitItem>
  </div>
</template>

<script setup>
import ExhibitItem from "@/components/exhibit/indexItem.vue";
const props = defineProps({
  list: {
    type: Array,
    required: true,
  },
});
const colors = ["#20E6A4", "#FF2E2E", "#905BFA", "#FFA63F"];
</script>

<style lang="scss" scoped>
.ExhibitItem {
  display: flex;
  overflow: auto;
  width: 100%;
  height: 100%;
  flex-direction: column;
  background: linear-gradient(
    180deg,
    rgba(7, 43, 35, 0.8) 76.54%,
    rgba(16, 81, 68, 0.8) 100%
  );
  box-shadow: 0px 0px 18px rgba(31, 250, 206, 0.45);
  border: 1px solid #1fface;
}
::-webkit-scrollbar {
  width: 4px;
  height: 4px;
}

::-webkit-scrollbar-track {
  border-radius: 0px;
  background: rgba(255, 255, 255, 0.08);
}

/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  border-radius: 0px;
  background: linear-gradient(
    180deg,
    #1ee7e7 0%,
    rgba(30, 231, 231, 0.35) 100%
  );
}
</style>
