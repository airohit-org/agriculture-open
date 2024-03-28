<template>
  <div class="farm-title">
    <div class="container">
        <span :style="{ zoom }" ref="textRef">{{ title }}</span>
    </div>
  </div>
</template>
<script setup>
import { fixTitleByZoom } from '@/utils';

import { ref, unref , watch, nextTick } from 'vue';

const props = defineProps({
  title: {
    type: String,
    default: ''
  },
})

const zoom = ref(1)
const textRef = ref()
watch(() => props.title, () => {
  nextTick(() => {
    const text = unref(textRef);
    zoom.value = fixTitleByZoom(text);
  })
})

</script>
<style scoped lang="scss">
.farm-title {
  position: absolute;
  width: 371.24px;
  height: 48.74px;
  background: linear-gradient(
    180deg,
    rgba(31, 250, 206, 0.16) 17.38%,
    rgba(28, 146, 128, 0.614844) 67.73%,
    #193f42 100%
  );
  clip-path: polygon(0 0, 100% 0, 93.5% 100%, 6.5% 100%);
  display: flex;
  align-items: center;
  justify-content: center;

  .container {
    width: 80%;
    height: 48.74px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  span {
    font-family: "YouSheBiaoTiHei";
    font-style: normal;
    font-weight: 400;
    font-size: 36px;
    line-height: 46px;
    background: linear-gradient(180deg, #ffffff 60.42%, #1fface 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    text-fill-color: transparent;
    text-shadow: 0px 4px 5px rgba(0, 0, 0, 0.27);
    white-space: nowrap;
  }
}
</style>
