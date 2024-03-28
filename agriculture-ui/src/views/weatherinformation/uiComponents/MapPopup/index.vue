<template>
  <div class="mappopup">
    <div class="mappopup-title">#{{ title }}</div>
    <PopInfo :info="info" v-show="popupShow" />
  </div>
</template>

<script setup>
import PopInfo from "@/components/PopInfo/index.vue";
const props = defineProps({
  title: String,
  info: Object,
  popupShow: Boolean,
});
</script>

<style lang="scss" scoped>
.mappopup {
  padding: 0;
  &-title {
    padding-left: 10px;
    padding-top: 12.5px;
    font-size: 24px;
    font-style: normal;
    font-weight: 400;
    font-family: "YouSheBiaoTiHei";
    color: #fff;
    // -webkit-text-stroke: 1px #000;
    padding-bottom: 12.5px;
    background-color: rgba(31, 250, 206, 0.4);
    background-image: -webkit-linear-gradient(
      -45deg,
      rgba(31, 250, 206, 0.1) 3%,
      transparent 3%,
      transparent 6%,
      rgba(31, 250, 206, 0.1) 6%,
      rgba(31, 250, 206, 0.1) 9%,
      transparent 9%,
      transparent 12%,
      rgba(31, 250, 206, 0.1) 12%,
      rgba(31, 250, 206, 0.1) 15%,
      transparent 15%,
      transparent 18%,
      rgba(31, 250, 206, 0.1) 18%,
      rgba(31, 250, 206, 0.1) 21%,
      transparent 21%,
      transparent 24%,
      rgba(31, 250, 206, 0.1) 24%,
      rgba(31, 250, 206, 0.1) 27%,
      transparent 27%,
      transparent 30%,
      rgba(31, 250, 206, 0.1) 30%,
      rgba(31, 250, 206, 0.1) 33%,
      transparent 33%,
      transparent 36%,
      rgba(31, 250, 206, 0.1) 36%,
      rgba(31, 250, 206, 0.1) 39%,
      transparent 39%,
      transparent 42%,
      rgba(31, 250, 206, 0.1) 42%,
      rgba(31, 250, 206, 0.1) 45%,
      transparent 45%,
      transparent 48%,
      rgba(31, 250, 206, 0.1) 48%,
      rgba(31, 250, 206, 0.1) 51%,
      transparent 51%,
      transparent 54%,
      rgba(31, 250, 206, 0.1) 54%,
      rgba(31, 250, 206, 0.1) 57%,
      transparent 57%,
      transparent 60%,
      rgba(31, 250, 206, 0.1) 60%,
      rgba(31, 250, 206, 0.1) 63%,
      transparent 63%,
      transparent 66%,
      rgba(31, 250, 206, 0.1) 66%,
      rgba(31, 250, 206, 0.1) 69%,
      transparent 69%,
      transparent 72%,
      rgba(31, 250, 206, 0.1) 72%,
      rgba(31, 250, 206, 0.1) 75%,
      transparent 75%,
      transparent 78%,
      rgba(31, 250, 206, 0.1) 78%,
      rgba(31, 250, 206, 0.1) 81%,
      transparent 81%,
      transparent 84%,
      rgba(31, 250, 206, 0.1) 84%,
      rgba(31, 250, 206, 0.1) 87%,
      transparent 87%,
      transparent 90%,
      rgba(31, 250, 206, 0.1) 90%,
      rgba(31, 250, 206, 0.1) 93%,
      transparent 93%,
      transparent 96%,
      rgba(31, 250, 206, 0.1) 96%,
      rgba(31, 250, 206, 0.1) 99%,
      transparent 99%,
      transparent
    );
    border-bottom: 1px solid rgba(31, 250, 206, 0.5);
    text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }
}
</style>
