<template>
  <div class="menu-item">
    <div class="border" :class="isActive ? 'border-active' : ''"></div>
    <div :class="isActive ? 'show' : 'hidden'" class="light"></div>
    <div class="text">{{ title }}</div>
    <div v-show="isShowClose" @click.prevent.stop="handleClose" class="close">X</div>
  </div>
</template>

<script setup>
const emit = defineEmits(["closeTag"]);
const props = defineProps({
  isActive: Boolean,
  title: String,
  isShowClose: Boolean
});
const handleClose = () => {
  emit("closeTag");
};
</script>

<style lang="scss" scoped>
// x = （2 + 根号3）* h / 2
$rate: 0.1339745962;
$height: 31.53px;

.menu-item {
  // margin-left: 100px;
  position: relative;
  height: $height;
  padding: 0 19.13px 0 18.81px;
  display: flex;
  align-items: center;

  .border {
    position: absolute;
    left: $rate * $height;
    right: $rate * $height;
    bottom: 0;
    top: 0;
    border: 1px solid #34fad2;
    transform: skew(-15deg);

    &-active {
      background: linear-gradient(
        174.13deg,
        rgba(123, 255, 252, 0.25) 25.38%,
        rgba(31, 250, 206, 0.52) 94.06%
      );
    }
  }

  .show {
    visibility: visible;
  }

  .hidden {
    visibility: hidden;
  }

  .light {
    width: 9px;
    height: 9px;
    position: relative;

    &::before {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      content: "";
      width: 9px;
      height: 9px;
      background: #1fface;
      filter: blur(2px);
      border-radius: 50%;
    }
    &::after {
      content: "";
      border-radius: 50%;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      width: 7px;
      height: 7px;
      background: #1fface;
    }
  }

  .text {
    margin-left: 10px;
    color: #fff;
  }

  .close {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    margin-left: 10px;
    width: 14px;
    height: 14px;
    z-index: 2;
    cursor: pointer;
    user-select: none;
  }
}
</style>
