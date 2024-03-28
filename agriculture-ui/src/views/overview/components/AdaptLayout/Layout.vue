<template>
  <div class="overview">
    <div class="bg">
      <slot name="bg"></slot>
    </div>
    <div class="light light-1"></div>
    <div class="light light-2"></div>
    <div class="light light-3"></div>
    <div class="light light-4"></div>
    <div class="item">
      <div class="left" ref="leftRef">
        <div class="total">
          <slot name="total"></slot>
        </div>
        <div class="left-1 module-item">
          <slot name="left-1"></slot>
        </div>
        <div class="left-2 module-item">
          <slot name="left-2"></slot>
        </div>
        <div class="left-3 module-item">
          <slot name="left-3"></slot>
        </div>
      </div>
    </div>
    <div class="center" v-if="showAccTempZone">
      <div class="info">
        <slot name="info" />
      </div>
      <div class="accTempZoneWrap">
        <slot name="accTempZone" />
      </div>
    </div>
    <div class="item">
      <div class="right" ref="rightRef">
        <!-- <div class="right-0 module-item">
          <slot name="right-0"></slot>
        </div> -->
        <div class="right-1 module-item">
          <slot name="right-1"></slot>
        </div>
        <div class="right-2 module-item">
          <slot name="right-2"></slot>
        </div>
      </div>
    </div>
    <div class="title">
      <slot name="title"></slot>
    </div>
    <div class="loading" v-if="loading">
      <slot name="loading"></slot>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { keepRatio } from "@/utils/keepRadio.js";
const props = defineProps({
  loading: {
    type: Boolean,
  },
  showAccTempZone: {
    type: Boolean,
  }
})
const leftRef = ref()
const rightRef = ref()
const initLeft = ref(undefined)
const leftResize = ref(undefined)
const initRight = ref(undefined)
const rightResize = ref(undefined)
onMounted(() => {
  const { init:onInitLeft,  onresize:onLeftResize } = keepRatio(
    leftRef.value,
    { initW: 458, initH: 1054 }
  );
  const { init:onInitRight,  onresize:onRightResize } = keepRatio(
    rightRef.value,
    { initW: 458, initH: 1054 }
  )
  initLeft.value = onInitLeft
  leftResize.value = onLeftResize
  initRight.value = onInitRight
  rightResize.value = onRightResize
  onInitLeft();
  onInitRight(); 
  window.addEventListener("resize", onLeftResize);
  window.addEventListener("resize", onRightResize);
})

onUnmounted(() => {
  window.removeEventListener("resize", leftResize.value);
  window.removeEventListener("resize", rightResize.value);
})

</script>

<style lang="scss" scoped>
@import "./index.scss";
</style>
