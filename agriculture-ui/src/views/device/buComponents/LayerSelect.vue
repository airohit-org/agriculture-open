<template>
  <div class="layer-select">
    <div
      v-for="({ label, value: itemValue, imgSrc, activeImgSrc }, index) in list"
      class="item"
      @click="handleClick(itemValue)"
      :class="modelValue?.includes(itemValue) ? 'active' : ''"
      :key="index"
    >
      <div>
        <img v-if="modelValue?.includes(itemValue)" :src="activeImgSrc" alt="" />
        <img v-else :src="imgSrc" alt="" />
        <div class="layerText" style="text-align: center">
          <span>{{ label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  list: Array,
  modelValue: Array,
});

const emit = defineEmits(["update:modelValue"]);
function handleClick(itemValue) {
  let res = props.modelValue;
  if (props.modelValue?.includes(itemValue)) {
    res = props.modelValue.filter((item) => item !== itemValue);
  } else {
    res = props.modelValue.concat(itemValue);
  }
  emit("update:modelValue", res);
}
</script>

<style lang="scss" scoped>
.layer-select {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  z-index: 99999;
  .item {
    cursor: pointer;
    display: flex;
    margin: 10px;
    div {
      &:first-child {
        span {
          font-style: normal;
          font-weight: 400;
          font-size: 14px;
          background: linear-gradient(180deg, #ffffff 34.38%, #1fface 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-fill-color: transparent;
          text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
        }
        img {
          height: 65px;
          width: 97px;
        }
      }
      &:last-child {
        img {
          &:first-child {
            width: 97px;
            height: 65px;
          }
          &:last-child {
            width: 97px;
            height: 65px;
          }
        }
      }
    }
  }

  .active {
    div {
      &:first-child {
        span {
          background: linear-gradient(
            180deg,
            #ffffff 34.38%,
            rgba(255, 209, 92, 0.92) 100%
          );
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
        }
      }

      &:last-child {
        background-image: url("");
      }
    }
  }
}
.layer-select::after {
  content: "";
  width: 1120px;
  height: 55px;
  position: absolute;
  left: -260px;
  bottom: -10px;
  background-image: url("https://oss.airoteach.cn/1af9def42d1d422cef2e409d5b960d78087b2194c3f67e94ff66b5b8660819b6.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  opacity: 0.5;
  z-index: -1;
}
</style>
