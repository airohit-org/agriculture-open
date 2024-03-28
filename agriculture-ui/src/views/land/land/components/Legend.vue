<template>
  <div class="legend">
    <div class="head" v-if="list.length">
      <span>图例</span>
    </div>
    <div class="itemList">
    <div class="item" v-for="({imageUrl, cropsName, code}) in list" :key="code" @click="$emit('handleIcon', code)">
      <SafeImage :src="imageUrl" alt="" defaultSrc="/images/crops/crop.png" />
      <span>{{ cropsName }}</span>
    </div>
    </div>
  </div>
</template>

<script setup>
import SafeImage from '@/components/SafeImage/index.vue';

const props = defineProps({
  list: {
    type: Array,
    default: () => [] 
  }
})
</script>

<style lang="scss" scoped>
.legend {
  width: 100%;
  height: 100%;
  display: flex;

  .head {
    align-self: flex-end;
    width: 61px;
    height: 31px;
    background-image: url('../../../../assets/images/legendHead.png');
    background-size: cover;
    position: relative;

    span {
      position: absolute;
      top: 0.3px;
      left: 17.72px;
      font-family: 'YouSheBiaoTiHei';
      font-style: normal;
      font-weight: 400;
      font-size: 11.8143px;
      line-height: 15px;
      letter-spacing: 1.57525px;
      color: #E3FAF8;
    }
  }

  .itemList {
    // background: blue;
    display: flex;
    background-position-y: 100%;
    background-repeat: no-repeat;
    background-size: 100% 31px;
    background-image: url('../../../../assets/images/legendBg.png');
  }

  .item {
    margin-right: 12px;
    display: flex;
    flex-direction: column;
    align-items: center;
    flex-wrap: nowrap;

    img {
      width: 32.5px;
      height: 43px;
    }

    span{
      margin-top: 5px;
      font-style: normal;
      font-weight: 500;
      color: #FFFFFF;
    }
  }
}
</style>
