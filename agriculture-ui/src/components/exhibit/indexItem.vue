<template>
  <div class="bg">
    <div class="icon-bg">
      <img class="icon" :src="imgSrc" />
    </div>
    <div class="title">
      {{ title }}
    </div>
    <div class="prompt">
      {{ prompt || "-" }}
    </div>
    <div
      class="light"
      :style="`background-image: linear-gradient(to right,transparent 0%, ${color} 50%,  transparent 100%);`"
    ></div>
  </div>
</template>

<script setup>
const props = defineProps({
  color: String,
  imgSrc: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  prompt: {
    type: String,
    default: "",
  },
});
</script>

<style lang="scss" scoped>
.bg {
  display: flex;
  align-items: center;
  width: 375px;
  height: 44px;
  flex-shrink: 0;
  background: rgba(255, 255, 255, 0.09);
  border-radius: 4px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  margin-bottom: 14px;
  margin-left: 18px;
  background-size: cover;
  position: relative;
  overflow: hidden;
  background-image: url("https://oss.airoteach.cn/60873680d893e9371087a32dbf6fe731e12c22404676a4542dce31142c7e7dbd.png");
  // transform: matrix(-1, 0, 0, 1, 0, 0);
  .icon-bg {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 28px;
    height: 28px;
    margin: 0 21px 0 26px;
    background: rgba(3, 49, 31, 0.71);
    box-shadow: 0px 0px 9px rgba(31, 250, 206, 0.64),
      inset 0px 0px 28px -10px #76f8dd;
    border: 1px solid #1fface;
    border-radius: 2px;
    .icon {
      width: 28px;
      height: 28px;
    }
  }
  .title {
    color: #ffffff;
    flex: 1;
  }
  .prompt {
    margin-right: 20px;
    color: #a7ffed;
    animation: scale2 1s linear forwards;
  }

  .light {
    animation: toDown 3s linear forwards;
    position: absolute;
    left: -100%;
    width: 100%;
    height: 100%;
    opacity: 0.38;
  }
}

.bg:first-child {
  margin-top: 29px;
}
.bg:last-child {
  margin-bottom: 57px;
}

@keyframes toDown {
  0% {
    left: -100%;
  }
  100% {
    left: 100%;
  }
}

@keyframes scale2 {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
</style>
