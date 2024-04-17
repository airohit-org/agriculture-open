<template>
  <div class="containerborder">
    <div class="btm" :style="`width:${width}px`">
      <div class="line">
        <div class="light light-fff"></div>
        <div class="light light-color" :style="`width: ${process}%`"></div>
        <div class="dash" :style="`width: ${process}%`">
          <div
            class="dot"
            :style="`
          width:${dotWidth / 2}px;
          margin-right:${dotWidth / 2}px;
          background-size: ${(process * width) / 100}px 100%;
          background-position-x: ${(item / eachNum) * 100}%;
          `"
            v-for="item in eachNum"
          ></div>
        </div>
        <div
          class="dash-fff"
          :style="`
        width: ${100 - process}%;
        background-size: ${dotWidth}px 100%;
        `"
        ></div>
      </div>
    </div>

    <div class="ddd"></div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
const process = ref(0)
const width = ref(1450)
const dotWidth = ref(8)

const eachNum = computed(() => {
  return ~~((process.value * width.value) / (100 * dotWidth.value));
})
</script>

<style lang="scss" scoped>
.containerborder {
  width: 100%;
  height: 100%;
  // background-color: #000;

  // .ddd {
  //   width: 200px;
  //   height: 200px;
  //   margin: 40px auto;
  //   background-image: linear-gradient(to right, red, blue);
  //   background-repeat: no-repeat;
  //   background-size: 400px 100%;
  //   background-position-x: 100%;
  // }

  .btm {
    height: 50.58px;
    position: relative;

    .line {
      height: 2px;
      width: 100%;
      position: absolute;
      top: 50%;
      transform: translateY(-50%);

      .light {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        height: 300%;
        width: 100%;
        filter: blur(2.5px);
        opacity: 0.4;
        &-fff {
          background-color: #ffffff;
        }
        &-color {
          background-image: linear-gradient(
            90deg,
            #73f2d1 0%,
            #77e7fe 48.96%,
            #07d1fa 100%
          );
        }
      }

      .dash {
        position: absolute;
        top: 0;
        width: 100%;
        height: 100%;
        display: flex;
        overflow: hidden;
        .dot {
          flex-shrink: 0;
          height: 100%;
          background-image: linear-gradient(
            90deg,
            #73f2d1 0%,
            #77e7fe 48.96%,
            #07d1fa 100%
          );
        }
      }

      .dash-fff {
        position: absolute;
        top: 0;
        right: 0;
        width: 100%;
        height: 100%;
        background-repeat: repeat-x;
        background-image: linear-gradient(
          to right,
          #fff 0%,
          #fff 50%,
          transparent 50%
        );
      }
    }
  }
}
</style>
