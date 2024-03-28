<template>
  <div class="map-light" :style="`z-index: ${zIndex}`">
    <div></div>
    <div></div>
    <div></div>
    <div></div>
  </div>
</template>
<script setup>
const props = defineProps({
  zIndex: {
    type: Number,
  },
});
</script>

<style scoped lang="scss">
$light-color-1: #000;
$light-color-2: rgba(20, 27, 31, 0.86);
$light-color-3: rgba(8, 27, 26, 0.72);
$light-color-4: rgba(8, 27, 26, 0.6);
$light-color-5: transparent;
$stage-1: 0%;
$stage-2: 28%;
$stage-3: 70%;
$stage-4: 82%;
$stage-5: 100%;
.map-light {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  pointer-events: none;

  div {
    &:nth-child(1) {
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      width: 36.13%;
      background: linear-gradient(
        90deg,
        $light-color-1 $stage-1,
        $light-color-2 $stage-2,
        $light-color-3 $stage-3,
        $light-color-4 $stage-4,
        $light-color-5 $stage-5
      );
    }
    &:nth-child(2) {
      position: absolute;
      right: 0;
      top: 0;
      bottom: 0;
      width: 36.13%;
      background: linear-gradient(
        270deg,
        $light-color-1 $stage-1,
        $light-color-2 $stage-2,
        $light-color-3 $stage-3,
        $light-color-4 $stage-4,
        $light-color-5 $stage-5
      );
    }
    &:nth-child(3) {
      position: absolute;
      right: 0;
      left: 0;
      top: 0;
      height: 13.21%;
      background: linear-gradient(
        180deg,
        #11181b 0%,
        $light-color-2 57%,
        $light-color-5 100%
      );
    }
    &:nth-child(4) {
      position: absolute;
      right: 0;
      left: 0;
      bottom: 0;
      height: 13.21%;
      background: linear-gradient(
        0deg,
        #11181b 0%,
        $light-color-2 57%,
        $light-color-5 100%
      );
    }
  }
}
</style>
