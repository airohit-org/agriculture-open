<template>
  <div class="warn-total">
    <div class="warn-total-top">
      <div class="number">{{ total }}</div>
    </div>
    <div class="warn-total-btm">{{ subTitle }}</div>
  </div>
</template>

<script setup>
const props = defineProps({
  subTitle: String,
  total: Number
})
</script>

<style lang="scss" scoped>
.warn-total {
  animation: heartBeat 2s ease-in-out  infinite forwards;
    &-top{
      width: 143px;
      height: 49px;
      background-image: url('https://oss.airoteach.cn/eab35dd9ac4c9f4f85f23dca466837d3e3036f498b6da8c3bf2a41a16ac09a18.png');
      background-size: cover;
      position: relative;
      .number {
        position: absolute;
        left: 76.56px;
        top: -9px;
        font-style: normal;
        font-weight: 700;
        font-size: 40px;
        color: #FFFFFF;  
      }
    }
    &-btm{
      margin-top: 4.51px;
      padding: 4px 25px;
      background: linear-gradient(180deg, rgba(255, 71, 71, 0.49) 0%, rgba(128, 36, 36, 0.72) 77.82%);
      font-family: 'PingFang SC';
      font-style: normal;
      font-weight: 500;
      font-size: 14px;
      color: #FFFFFF;
      clip-path: polygon(5.5px 0%, 100% 0%, calc(100% - 5.5px) 100%, 0% 100%);
  }
}

@keyframes heartBeat {
  0%{
    transform: scale(1);
  }
  5%{
    transform: scale(1.1);
  }
  10%, 100%{
    transform: scale(1);
  }
  
}
</style>
