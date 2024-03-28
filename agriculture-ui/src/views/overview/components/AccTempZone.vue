<template>
  <div class="acc-temp-zone">
    <div class="top">
      <div class="top-content"></div>
    </div>
    <div class="bottom">
      <div
        class="item"
        :class="active === index ? 'item-active' : ''"
        v-for="(title, index) in list"
      >
        {{ title }}
        <div v-if="active === index" class="dot"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  active: Number
})

let list = [
  "第一积温带",
  "第二积温带",
  "第三积温带",
  "第四积温带",
  "第五积温带",
  "第六积温带",
]
</script>

<style scoped lang="scss">
.acc-temp-zone {
  width: 100%;
  height: 100%;
  //   border: 1px solid red;

  .top {
    width: 100%;
    height: 23px;
    border: 2px solid;
    border-image-slice: 2;
    border-image-source: linear-gradient(
      90deg,
      rgba(31, 250, 206, 0) 0%,
      rgba(31, 250, 206, 0.452632) 21.74%,
      #1fface 48.04%,
      rgba(31, 250, 206, 0) 97.09%
    );
    display: flex;
    align-items: center;
    justify-content: center;
    &-content {
      width: calc(100% - 92px);
      height: 7px;

      background: linear-gradient(
        90deg,
        #f05b25 0%,
        #f69417 21.35%,
        #e4c59a 39.58%,
        #fdf001 59.38%,
        #8bc93f 78.13%,
        #8ed9f7 100%
      );
    }
  }

  .bottom {
    width: calc(100% - 134px);
    margin: 7px auto 0px;
    display: flex;
    flex-wrap: nowrap;
    align-items: center;
    justify-content: space-around;
    .item {
      font-family: "PingFang SC";
      font-style: normal;
      font-weight: 400;
      font-size: 12px;
      line-height: 17px;
      height: 17px;
      color: #ffffff;
      position: relative;
      white-space: nowrap;

      .dot {
        position: absolute;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        left: 50%;
        top: -28px;
        transform: translateX(-50%);
        border: 2px solid #ffffff;
        background: #1fface;
        border: 2px solid #ffffff;
        box-shadow: inset 0px 5px 3px #c6fff3;

        &-0 {
          background-color: rgb(229, 131, 58);
          box-shadow: inset 0px 5px 3px rgb(229, 131, 58, 0.2);
        }
        &-1 {
          background-color: rgb(230, 164, 85);
          box-shadow: inset 0px 5px 3px rgba(230, 164, 85, 0.2);
        }
        &-2 {
          background-color: rgb(224, 200, 155);
          box-shadow: inset 0px 5px 3px rgba(224, 200, 155, 0.2);
        }
        &-3 {
          background-color: rgb(246, 233, 94);
          box-shadow: inset 0px 5px 3px rgba(246, 233, 94, 0.2);
        }
        &-4 {
          background-color: rgb(177, 210, 84);
          box-shadow: inset 0px 5px 3px rgba(177, 210, 84, 0.2);
        }
        &-5 {
          background-color: rgb(156, 207, 59);
          box-shadow: inset 0px 5px 3px rgba(156, 207, 59, 0.2);
        }
      }
    }

    .item-active {
      color: #1fface;
    }
  }
}
</style>
