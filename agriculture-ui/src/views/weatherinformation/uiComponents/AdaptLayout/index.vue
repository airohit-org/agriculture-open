<template>
  <div class="weatherinfomation">
    <div class="bg">
      <slot name="bg"></slot>
    </div>
    <div class="light light-1"></div>
    <div class="light light-2"></div>
    <div class="light light-3"></div>
    <div class="top">
      <div class="item">
        <div class="left" ref="leftRef">
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
      <div class="center">
        <slot name="center"></slot>
      </div>
      <div class="item">
        <div class="right" ref="rightRef">
          <div class="right-1 module-item">
            <slot name="right-1"></slot>
          </div>
          <div class="right-2 module-item">
            <slot name="right-2"></slot>
          </div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <slot name="bottom"></slot>
    </div>
    <div class="loading" v-if="loading">
      <slot name="loading"></slot>
    </div>
  </div>
</template>
<script setup>
import { keepRatio } from "@/utils/keepRadio.js";

const props = defineProps({
  loading: Boolean,
});
const initLeft = ref(undefined);
const leftResize = ref(undefined);
const initRight = ref(undefined);
const rightResize = ref(undefined);

const leftRef = ref();
const rightRef = ref();
onMounted(() => {
  const { init: onInitLeft, onresize: onLeftResize } = keepRatio(
    leftRef.value,
    { initW: 458, initH: 906 }
  );
  const { init: onInitRight, onresize: onRightResize } = keepRatio(
    rightRef.value,
    { initW: 458, initH: 906 }
  );
  initLeft.value = onInitLeft;
  leftResize.value = onLeftResize;
  initRight.value = onInitRight;
  rightResize.value = onRightResize;
  initLeft.value();
  initRight.value();
  window.addEventListener("resize", onLeftResize);
  window.addEventListener("resize", onRightResize);
}),
  onUnmounted(() => {
    window.removeEventListener("resize", leftResize.VALUE);
    window.removeEventListener("resize", rightResize.value);
  });
</script>
<style scoped lang="scss">
@import "./index.scss";
</style>
